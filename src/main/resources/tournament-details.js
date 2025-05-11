document.addEventListener("DOMContentLoaded", () => {
    const API_BASE = "http://localhost:8080/api";
    const tournamentId = new URLSearchParams(window.location.search).get("id");

    const nameEl = document.getElementById("tournament-name");
    const sportEl = document.getElementById("tournament-sport");
    const startEl = document.getElementById("tournament-start");
    const endEl = document.getElementById("tournament-end");
    const winnerEl = document.getElementById("tournament-winner");
    const teamsList = document.getElementById("tournament-teams");

    if (!tournamentId) {
        alert("Tournament ID is missing in the URL.");
        return;
    }

    // Load tournament details
    fetch(`${API_BASE}/tournaments/${tournamentId}`)
        .then(res => res.json())
        .then(tournament => {
            nameEl.textContent = tournament.name;
            sportEl.textContent = tournament.sport;
            startEl.textContent = tournament.startDate;
            endEl.textContent = tournament.endDate;
            winnerEl.textContent = tournament.winner || "TBD";
        })
        .catch(err => {
            console.error("Failed to fetch tournament:", err);
            alert("Could not load tournament data.");
        });

    // Load associated teams
    fetch(`${API_BASE}/tournaments/${tournamentId}/teams`)
        .then(res => res.json())
        .then(teams => {
            teamsList.innerHTML = "";
            teams.forEach(team => {
                const li = document.createElement("li");
                li.textContent = team.name;
                teamsList.appendChild(li);
            });
        })
        .catch(err => {
            console.error("Failed to fetch teams:", err);
        });
});
