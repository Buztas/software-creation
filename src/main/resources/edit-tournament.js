document.addEventListener("DOMContentLoaded", () => {
    const tournamentId = getTournamentIdFromUrl();
    const form = document.getElementById("edit-tournament-form");
    const teamList = document.getElementById("tournament-teams");
    const deleteBtn = document.getElementById("delete-tournament");

    if (!tournamentId) {
        alert("Tournament ID is missing from the URL.");
        return;
    }

    fetchTournament(tournamentId);
    fetchTeams(tournamentId);

    // Update tournament
    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const updatedTournament = {
            id: tournamentId,
            name: form.name.value,
            sport: form.sport.value,
            startDate: form.startDate.value,
            endDate: form.endDate.value,
            winner: form.winner.value
        };

        try {
            const res = await fetch(`/api/tournaments/${tournamentId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(updatedTournament)
            });

            if (res.ok) {
                alert("Tournament updated successfully.");
            } else {
                alert("Failed to update tournament.");
            }
        } catch (err) {
            console.error("Error updating tournament:", err);
        }
    });

    // Delete tournament
    deleteBtn.addEventListener("click", async () => {
        if (!confirm("Are you sure you want to delete this tournament?")) return;

        try {
            const res = await fetch(`/api/tournaments/${tournamentId}`, {
                method: "DELETE"
            });

            if (res.ok) {
                alert("Tournament deleted.");
                window.location.href = "/index.html"; // Redirect after deletion
            } else {
                alert("Failed to delete tournament.");
            }
        } catch (err) {
            console.error("Error deleting tournament:", err);
        }
    });

    // Load tournament data
    async function fetchTournament(id) {
        try {
            const res = await fetch(`/api/tournaments/${id}`);
            if (!res.ok) throw new Error("Tournament not found");

            const data = await res.json();
            form.name.value = data.name;
            form.sport.value = data.sport;
            form.startDate.value = data.startDate;
            form.endDate.value = data.endDate;
            form.winner.value = data.winner || "";
        } catch (err) {
            console.error("Error fetching tournament:", err);
        }
    }

    // Load associated teams
    async function fetchTeams(id) {
        try {
            const res = await fetch(`/api/tournaments/${id}/teams`);
            const teams = await res.json();

            teamList.innerHTML = "";
            const winnerSelect = document.getElementById("winner");
            winnerSelect.innerHTML = ""; // Clear old options

            teams.forEach(team => {
                // Add to team list
                const li = document.createElement("li");
                li.className = "list-group-item d-flex justify-content-between align-items-center";
                li.innerHTML = `
        ${team.name}
        <button class="btn btn-danger btn-sm">Remove</button>`;
                li.querySelector("button").addEventListener("click", () => removeTeam(id, team.id));
                teamList.appendChild(li);

                // Add to winner dropdown
                const option = document.createElement("option");
                option.value = team.name;
                option.textContent = team.name;
                winnerSelect.appendChild(option);
            });
        } catch (err) {
            console.error("Error loading teams:", err);
        }
    }

    // Remove a team
    async function removeTeam(tournamentId, teamId) {
        if (!confirm("Remove this team from the tournament?")) return;

        try {
            const res = await fetch(`/api/tournaments/${tournamentId}/remove-team/${teamId}`, {
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
