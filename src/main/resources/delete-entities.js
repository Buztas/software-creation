document.addEventListener("DOMContentLoaded", () => {
    document.body.addEventListener("click", async (event) => {
        const button = event.target.closest(".btn-danger[data-type][data-id]");
        if (!button) return;

        const entityType = button.getAttribute("data-type");
        const entityId = button.getAttribute("data-id");

        if (!confirm(`Are you sure you want to delete this ${entityType}?`)) {
            return;
        }

        let apiUrl;
        switch (entityType) {
            case "player":
                apiUrl = `/api/players/${entityId}`;
                break;
            case "team":
                apiUrl = `/api/teams/${entityId}`;
                break;
            case "tournament":
                apiUrl = `/api/tournaments/${entityId}`;
                break;
            default:
                console.error("Unknown entity type:", entityType);
                return;
        }

        try {
            const res = await fetch(apiUrl, { method: "DELETE" });
            if (res.ok) {
                alert(`${capitalize(entityType)} deleted successfully.`);
                location.reload();
            } else {
                alert(`Failed to delete ${entityType}.`);
            }
        } catch (err) {
            console.error(`Error deleting ${entityType}:`, err);
        }
    });

    function capitalize(word) {
        return word.charAt(0).toUpperCase() + word.slice(1);
    }
});
