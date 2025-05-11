document.addEventListener("DOMContentLoaded", () => {
    const API_BASE = "http://localhost:8080/api";
    const form = document.getElementById("add-team-form");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const team = {
            name: form.name.value,
            sport: form.sport.value
        };

        try {
            const res = await fetch(`${API_BASE}/teams`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(team)
            });

            if (res.ok || res.status === 201) {
                alert("Team added successfully!");
                window.location.href = "/index.html";
            } else {
                alert("Failed to add team.");
            }
        } catch (err) {
            console.error("Error adding team:", err);
        }
    });
});
