document.addEventListener("DOMContentLoaded", () => {
    const API_BASE = "http://localhost:8081/api";

    const tournamentTableBody = document.getElementById("tournament-table-body");
    const teamTableBody = document.getElementById("team-table-body");
    const playerTableBody = document.getElementById("player-table-body");

    fetch(`${API_BASE}/tournaments`)
        .then(res => res.json())
        .then(tournaments => {
            tournamentTableBody.innerHTML = "";
            tournaments.forEach(tournament => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${tournament.id}</td>
                    <td>${tournament.name}</td>
                    <td>${tournament.sport}</td>
                    <td>${tournament.startDate}</td>
                    <td>${tournament.endDate}</td>
                    <td>
                        <a href="tournament-details.html?id=${tournament.id}" class="btn btn-info btn-sm">View</a>
                        <a href="edit-tournament.html?id=${tournament.id}" class="btn btn-warning btn-sm">Edit</a>
                        <button class="btn btn-danger btn-sm" data-type="tournament" data-id="${tournament.id}">Delete</button>
                    </td>
                `;
                tournamentTableBody.appendChild(row);
            });
        })
        .catch(err => console.error("Failed to load tournaments:", err));

    fetch(`${API_BASE}/teams`)
        .then(res => res.json())
        .then(teams => {
            teamTableBody.innerHTML = "";
            teams.forEach(team => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${team.id}</td>
                    <td>${team.name}</td>
                    <td>${team.sport}</td>
                    <td>
                        <a href="team-details.html?id=${team.id}" class="btn btn-info btn-sm">View</a>
                        <a href="edit-team.html?id=${team.id}" class="btn btn-warning btn-sm">Edit</a>
                        <button class="btn btn-danger btn-sm" data-type="team" data-id="${team.id}">Delete</button>
                    </td>
                `;
                teamTableBody.appendChild(row);
            });
        })
        .catch(err => console.error("Failed to load teams:", err));

    fetch(`${API_BASE}/players`)
        .then(res => res.json())
        .then(async players => {
            playerTableBody.innerHTML = "";
            for (const player of players) {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${player.id}</td>
                    <td>${player.name}</td>
                    <td>${player.surname}</td>
                    <td>${player.age}</td>
                    <td>
                        <a href="edit-player.html?id=${player.id}" class="btn btn-warning btn-sm">Edit</a>
                        <button class="btn btn-danger btn-sm" data-type="player" data-id="${player.id}">Delete</button>
                    </td>
                `;
                playerTableBody.appendChild(row);
            }
        })
        .catch(err => console.error("Failed to load players:", err));
});
