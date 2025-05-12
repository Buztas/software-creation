document.addEventListener("DOMContentLoaded", () => {
    const API_BASE = "http://localhost:8080/api";
    const tournamentId = getTournamentIdFromUrl();
    const form = document.getElementById("edit-tournament-form");
    const teamList = document.getElementById("tournament-teams");
    const deleteBtn = document.getElementById("delete-tournament");
    const availableTeamContainer = document.getElementById("available-teams-checkboxes");
    const addTeamsBtn = document.getElementById("add-teams-btn");

    if (!tournamentId) {
        alert("Tournament ID is missing from the URL.");
        return;
    }

    fetchTournament(tournamentId);
    fetchTeams(tournamentId);
    fetchAvailableTeams(tournamentId);

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
            const res = await fetch(`${API_BASE}/tournaments/${tournamentId}`, {
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

    // Add selected teams
    addTeamsBtn.addEventListener("click", async () => {
        const selectedIds = Array.from(
            availableTeamContainer.querySelectorAll("input[type='checkbox']:checked")
        ).map(cb => cb.value);

        try {
            await Promise.all(selectedIds.map(teamId =>
                fetch(`${API_BASE}/tournaments/${tournamentId}/add-team?teamId=${teamId}`, {
                    method: "POST"
                })
            ));
            alert("Teams added!");
            fetchTeams(tournamentId);
            fetchAvailableTeams(tournamentId);
        } catch (err) {
            console.error("Error adding teams:", err);
            alert("Failed to add selected teams.");
        }
    });

    // Load tournament data
    async function fetchTournament(id) {
        try {
            const res = await fetch(`${API_BASE}/tournaments/${id}`);
            if (!res.ok) throw new Error("Tournament not found");

            const data = await res.json();
            form.name.value = data.name;
            form.sport.value = data.sport;
            form.startDate.value = data.startDate?.slice(0, 10) || "";
            form.endDate.value = data.endDate?.slice(0, 10) || "";
            form.winner.value = data.winner || "";
        } catch (err) {
            console.error("Error fetching tournament:", err);
        }
    }

    // Load current teams
    async function fetchTeams(id) {
        try {
            const res = await fetch(`${API_BASE}/tournaments/${id}/teams`);
            const teams = await res.json();

            teamList.innerHTML = "";
            const winnerSelect = document.getElementById("winner");
            winnerSelect.innerHTML = "";

            teams.forEach(team => {
                const li = document.createElement("li");
                li.className = "list-group-item d-flex justify-content-between align-items-center";
                li.innerHTML = `
        ${team.name}
        <button type="button" class="btn btn-danger btn-sm remove-team-btn" data-id="${team.id}">Remove</button>`;
                teamList.appendChild(li);

                const option = document.createElement("option");
                option.value = team.name;
                option.textContent = team.name;
                if (team.name === form.winner.value) option.selected = true;
                winnerSelect.appendChild(option);

                const removeBtn = li.querySelector(".remove-team-btn");
                if (removeBtn) {
                    removeBtn.addEventListener("click", () => removeTeam(id, team.id));
                }
            });

        } catch (err) {
            console.error("Error loading teams:", err);
        }
    }

    // Remove a team
    async function removeTeam(tournamentId, teamId) {
        if (!confirm("Remove this team from the tournament?")) return;

        console.log(`Removing team ${teamId} from tournament ${tournamentId}`);

        try {
            const res = await fetch(`${API_BASE}/tournaments/${tournamentId}/remove-team/${teamId}`, {
                method: "DELETE"
            });

            if (res.ok) {
                fetchTeams(tournamentId);
                fetchAvailableTeams(tournamentId);
            } else {
                alert("Failed to remove team.");
            }
        } catch (err) {
            console.error("Error removing team:", err);
        }
    }

    // Load available teams
    async function fetchAvailableTeams(tournamentId) {
        try {
            const res = await fetch(`${API_BASE}/tournaments/${tournamentId}/available-teams`);
            const teams = await res.json();

            availableTeamContainer.innerHTML = "";
            teams.forEach(team => {
                const wrapper = document.createElement("div");
                wrapper.className = "form-check";

                const checkbox = document.createElement("input");
                checkbox.className = "form-check-input";
                checkbox.type = "checkbox";
                checkbox.id = `team-${team.id}`;
                checkbox.value = team.id;

                const label = document.createElement("label");
                label.className = "form-check-label";
                label.htmlFor = checkbox.id;
                label.textContent = team.name;

                wrapper.appendChild(checkbox);
                wrapper.appendChild(label);
                availableTeamContainer.appendChild(wrapper);
            });
        } catch (err) {
            console.error("Error loading available teams:", err);
        }
    }

    function getTournamentIdFromUrl() {
        const params = new URLSearchParams(window.location.search);
        return params.get("id");
    }
});
