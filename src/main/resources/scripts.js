document.addEventListener('DOMContentLoaded', () => {
    const API_BASE = 'http://localhost:8081/api';
    const form = document.getElementById('add-tournament-form');
    const teamContainer = document.getElementById('team-checkboxes');

    fetch(`${API_BASE}/teams`)
        .then(res => res.json())
        .then(teams => {
            teams.forEach(team => {
                const wrapper = document.createElement('div');
                wrapper.className = 'form-check';

                const checkbox = document.createElement('input');
                checkbox.className = 'form-check-input';
                checkbox.type = 'checkbox';
                checkbox.id = `team-${team.id}`;
                checkbox.value = team.id;

                const label = document.createElement('label');
                label.className = 'form-check-label';
                label.htmlFor = checkbox.id;
                label.textContent = team.name;

                wrapper.appendChild(checkbox);
                wrapper.appendChild(label);
                teamContainer.appendChild(wrapper);
            });
        })
        .catch(err => {
            console.error('Failed to load teams:', err);
            alert('Could not load teams for selection.');
        });

    form.addEventListener('submit', async function (event) {
        event.preventDefault();

        const tournament = {
            name: form.name.value,
            sport: form.sport.value,
            startDate: form.startDate.value,
            endDate: form.endDate.value,
            winner: form.winner.value || null
        };

        try {
            const res = await fetch(`${API_BASE}/tournaments`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(tournament)
            });

            if (!res.ok) {
                alert('Failed to add tournament.');
                return;
            }

            const newTournament = await res.json();

            const selectedTeamIds = Array.from(
                teamContainer.querySelectorAll('input[type="checkbox"]:checked')
            ).map(cb => cb.value);

            await Promise.all(selectedTeamIds.map(teamId =>
                fetch(`${API_BASE}/tournaments/${newTournament.id}/add-team?teamId=${teamId}`, {
                    method: 'POST'
                })
            ));

            alert('Tournament added successfully with teams!');
            window.location.href = '/index.html';

        } catch (error) {
            console.error('❌ Error adding tournament:', error);
            alert('An error occurred while adding the tournament.');
        }
    });
});
