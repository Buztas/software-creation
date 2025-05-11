document.addEventListener("DOMContentLoaded", () => {
    const API_BASE = "http://localhost:8080/api";
    const urlParams = new URLSearchParams(window.location.search);
    const teamId = urlParams.get("id");

    const form = document.getElementById("edit-team-form");
    const nameInput = document.getElementById("name");
    const sportInput = document.getElementById("sport");

    if (!teamId) {
        alert("Missing team ID in URL.");
        return;
    }

    // Load existing team data
    fetch(`${API_BASE}/teams/${teamId}`)
        .then(res => {
            if (!res.ok) throw new Error("Team not found");
            return res.json();
        })
        .then(team => {
            nameInput.value = team.name;
            sportInput.value = team.sport;
        })
        .catch(err => {
            console.error("Error loading team:", err);
            alert("Could not load team data.");
        });

    // Handle form submission
    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const updatedTeam = {
            id: teamId,
            name: nameInput.value,
            sport: sportInput.value
        };

        try {
            const res = await fetch(`${API_BASE}/teams/${teamId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(updatedTeam)
            });

            if (res.ok) {
                alert("Team updated successfully.");
                window.location.href = "/index.html";
            } else {
                alert("Failed to update team.");
            }
        } catch (err) {
            console.error("Update error:", err);
        }
    });
});
