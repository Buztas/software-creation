document.addEventListener("DOMContentLoaded", () => {
    const API_BASE = "http://localhost:8081/api";
    const urlParams = new URLSearchParams(window.location.search);
    const teamId = urlParams.get("id");

    const form = document.getElementById("edit-team-form");
    const nameInput = document.getElementById("name");
    const sportInput = document.getElementById("sport");
    const versionInput = document.getElementById("team-version");

    if (!teamId) {
        alert("Missing team ID in URL.");
        return;
    }

    fetch(`${API_BASE}/teams/${teamId}`)
        .then(res => {
            if (!res.ok) throw new Error("Team not found");
            return res.json();
        })
        .then(team => {
            nameInput.value = team.name;
            sportInput.value = team.sport;
            versionInput.value = team.version;
        })
        .catch(err => {
            console.error("Error loading team:", err);
            alert("Could not load team data.");
        });

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const updatedTeam = {
            id: teamId,
            name: nameInput.value,
            sport: sportInput.value,
            version: parseInt(versionInput.value)
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
