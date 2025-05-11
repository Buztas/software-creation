document.addEventListener('DOMContentLoaded', () => {
    const API_BASE = 'http://localhost:8080/api';

    const addTournamentForm = document.querySelector('#add-tournament-form');

    if (addTournamentForm) {
        addTournamentForm.addEventListener('submit', async function (event) {
            event.preventDefault();

            const tournament = {
                name: addTournamentForm.name.value,
                sport: addTournamentForm.sport.value,
                startDate: addTournamentForm.startDate.value,
                endDate: addTournamentForm.endDate.value,
                winner: addTournamentForm.winner.value || null
            };

            try {
                const res = await fetch(`${API_BASE}/tournaments`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(tournament)
                });

                if (res.ok) {
                    alert('Tournament added successfully!');
                    window.location.href = '/index.html';
                } else {
                    alert('Failed to add tournament.');
                }
            } catch (error) {
                console.error('❌ Error adding tournament:', error);
                alert('An error occurred while adding the tournament.');
            }
        });
    }
});
