document.addEventListener("DOMContentLoaded", () => {
    const API_BASE = "http://localhost:8080/api";
    const tournamentId = getTournamentIdFromUrl();
    const form = document.getElementById("edit-tournament-form");
    const teamList = document.getElementById("tournament-teams");
    const deleteBtn = document.getElementById("delete-tournament");
    const winnerSelect = document.getElementById("winner");

    if (!tournamentId) {
        alert("Tournament ID is missing from the URL.");
        return;
    }

    // Fetch tournament first, then teams
    (async () => {
        await fetchTournament(tournamentId);
        await fetchTeams(tournamentId);
    })();

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const updatedTournament = {
            id: tournamentId,
            name: form.name.value,
            sport: form.sport.value,
            startDate: form.startDate.value,
            endDate: form.endDate.value,
            winner: winnerSelect.value || null
        };

        try {
            const res = await fetch(`${API_BASE}/tournaments/${tournamentId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(updatedTournament)
            });

            if (res.ok) {
                alert("Tournament updated successfully.");
                window.location.href = "/index.html";
            } else {
                alert("Failed to update tournament.");
            }
        } catch (err) {
            console.error("Error updating tournament:", err);
        }
    });

    deleteBtn.addEventListener("click", async () => {
        if (!confirm("Are you sure you want to delete this tournament?")) return;

        try {
            const res = await fetch(`${API_BASE}/tournaments/${tournamentId}`, {
                method: "DELETE"
            });

            if (res.ok) {
                alert("Tournament deleted.");
                window.location.href = "/index.html";
            } else {
                alert("Failed to delete tournament.");
            }
        } catch (err) {
            console.error("Error deleting tournament:", err);
        }
    });

    async function fetchTournament(id) {
        try {
            const res = await fetch(`${API_BASE}/tournaments/${id}`);
            if (!res.ok) throw new Error("Tournament not found");

            const data = await res.json();
            form.name.value = data.name || "";
            form.sport.value = data.sport || "";
            form.startDate.value = data.startDate?.slice(0, 10) || "";
            form.endDate.value = data.endDate?.slice(0, 10) || "";
            winnerSelect.dataset.currentWinner = data.winner || "";
        } catch (err) {
            console.error("Error fetching tournament:", err);
        }
    }

    async function fetchTeams(id) {
        try {
            const res = await fetch(`${API_BASE}/tournaments/${id}/teams`);
            const teams = await res.json();

            teamList.innerHTML = "";
            winnerSelect.innerHTML = '<option value="">-- Select Winner (Optional) --</option>';
            const currentWinner = winnerSelect.dataset.currentWinner;

            teams.forEach(team => {
                // Team list
                const li = document.createElement("li");
                li.className = "list-group-item d-flex justify-content-between align-items-center";
                li.innerHTML = `
                    ${team.name}
                    <button class="btn btn-danger btn-sm">Remove</button>`;
                li.querySelector("button").addEventListener("click", () => removeTeam(id, team.id));
                teamList.appendChild(li);

                // Winner dropdown
                const option = document.createElement("option");
                option.value = team.name;
                option.textContent = team.name;
                if (team.name === currentWinner) option.selected = true;
                winnerSelect.appendChild(option);
            });

        } catch (err) {
            console.error("Error loading teams:", err);
        }
    }

    async function removeTeam(tournamentId, teamId) {
        if (!confirm("Remove this team from the tournament?")) return;

        try {
            const res = await fetch(`${API_BASE}/tournaments/${tournamentId}/remove-team/${teamId}`, {
                method: "DELETE"
            });

            if (res.ok) {
                fetchTeams(tournamentId);
            } else {
                alert("Failed to remove team.");
            }
        } catch (err) {
            console.error("Error removing team:", err);
        }
    }

    function getTournamentIdFromUrl() {
        const params = new URLSearchParams(window.location.search);
        return params.get("id");
    }
});
