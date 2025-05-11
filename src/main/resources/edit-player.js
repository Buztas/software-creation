document.addEventListener("DOMContentLoaded", () => {
    const API_BASE = "http://localhost:8080/api";
    const urlParams = new URLSearchParams(window.location.search);
    const playerId = urlParams.get("id");

    const form = document.getElementById("edit-player-form");
    const nameInput = document.getElementById("name");
    const surnameInput = document.getElementById("surname");
    const ageInput = document.getElementById("age");
    const teamSelect = document.getElementById("team");

    if (!playerId) {
        alert("Missing player ID in URL.");
        return;
    }

    // Load teams into the select dropdown
    fetch(`${API_BASE}/teams`)
        .then(res => res.json())
        .then(teams => {
            teams.forEach(team => {
                const option = document.createElement("option");
                option.value = team.id;
                option.textContent = team.name;
                teamSelect.appendChild(option);
            });
        })
        .catch(err => {
            console.error("Failed to load teams:", err);
            alert("Could not load teams.");
        });

    // Load existing player data
    fetch(`${API_BASE}/players/${playerId}`)
        .then(res => {
            if (!res.ok) throw new Error("Player not found");
            return res.json();
        })
        .then(player => {
            nameInput.value = player.name;
            surnameInput.value = player.surname;
            ageInput.value = player.age;
            teamSelect.value = player.team?.id || "";
        })
        .catch(err => {
            console.error("Error loading player:", err);
            alert("Could not load player data.");
        });

    // Handle form submission
    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const updatedPlayer = {
            id: playerId,
            name: nameInput.value,
            surname: surnameInput.value,
            age: parseInt(ageInput.value),
            team: {
                id: parseInt(teamSelect.value)
            }
        };

        try {
            const res = await fetch(`${API_BASE}/players/${playerId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(updatedPlayer)
            });

            if (res.ok) {
                alert("Player updated successfully.");
                window.location.href = "/index.html";
            } else {
                alert("Failed to update player.");
            }
        } catch (err) {
            console.error("Update error:", err);
        }
    });
});
