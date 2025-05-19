document.addEventListener("DOMContentLoaded", () => {
    const API_BASE = "http://localhost:8081/api";
    const form = document.getElementById("add-player-form");
    const teamSelect = document.getElementById("team");

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
            alert("Could not load teams. Please try again.");
        });

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const player = {
            name: form.name.value,
            surname: form.surname.value,
            age: parseInt(form.age.value, 10),
            team: {
                id: parseInt(form.team.value, 10)
            }
        };

        try {
            const res = await fetch(`${API_BASE}/players`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(player)
            });

            if (res.ok) {
                alert("Player added successfully!");
                window.location.href = "/index.html";
            } else {
                alert("Failed to add player.");
            }
        } catch (err) {
            console.error("Error adding player:", err);
        }
    });
});
