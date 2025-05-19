document.addEventListener("DOMContentLoaded", () => {
    const API_BASE = "http://localhost:8081/api";
    const teamId = new URLSearchParams(window.location.search).get("id");

    const teamIdSpan = document.getElementById("team-id");
    const teamNameSpan = document.getElementById("team-name");
    const teamSportSpan = document.getElementById("team-sport");
    const playersTable = document.getElementById("players-table-body");
    const tournamentList = document.getElementById("tournament-list");

    if (!teamId) {
        alert("Missing team ID.");
        return;
    }

    fetch(`${API_BASE}/teams/${teamId}`)
        .then(res => res.json())
        .then(team => {
            teamIdSpan.textContent = team.id;
            teamNameSpan.textContent = team.name;
            teamSportSpan.textContent = team.sport;

            playersTable.innerHTML = "";
            (team.players || []).forEach(player => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${player.id}</td>
                    <td>${player.name}</td>
                    <td>${player.surname}</td>
                    <td>${player.age}</td>
                `;
                playersTable.appendChild(row);
            });
        })
        .catch(err => {
            console.error("Failed to load team:", err);
            alert("Could not load team data.");
        });

    fetch(`${API_BASE}/teams/${teamId}/tournaments`)
        .then(res => res.json())
        .then(tournaments => {
            tournamentList.innerHTML = "";
            tournaments.forEach(tournament => {
                const li = document.createElement("li");
                li.textContent = tournament.name;
                tournamentList.appendChild(li);
            });
        })
        .catch(err => {
            console.error("Failed to load tournaments:", err);
        });
});
