// Load and display blacklist
async function loadBlacklist() {
  try {
    const response = await fetch('/api/blacklist/list', {
      method: 'POST'
    });

    if (response.ok) {
      const blacklist = await response.json();
      const listCard = document.querySelector('.list-card');
      listCard.innerHTML = ''; // Clear existing items

      blacklist.forEach(item => {
        const domainSpan = document.createElement('div');
        domainSpan.className = 'label block';
        domainSpan.dataset.id = item.id; // Store the ID in the DOM element
        
        const domainText = document.createElement('span');
        domainText.textContent = item.domainName;
        domainSpan.appendChild(domainText);

        const deleteBtn = document.createElement('button');
        deleteBtn.className = 'delete-btn';
        deleteBtn.innerHTML = '×';
        deleteBtn.title = 'Remove from blocklist';
        deleteBtn.onclick = (e) => {
          const id = e.currentTarget.parentElement.dataset.id;
          deleteFromBlacklist(id);
        };
        domainSpan.appendChild(deleteBtn);

        listCard.appendChild(domainSpan);
      });
    } else {
      console.error('Failed to load blacklist');
    }
  } catch (error) {
    console.error('Error loading blacklist:', error);
  }
}

// Add domain to blacklist
async function addToBlacklist(domain) {
  try {
    const response = await fetch('/api/blacklist/add', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: `domainname=${encodeURIComponent(domain)}`
    });

    const data = await response.json();

    if (response.ok && data.id) {
      alert('Domain added to blacklist successfully');
      loadBlacklist(); // Refresh the list
      return true;
    } else {
      const message = data.message || 'Failed to add domain to blacklist';
      alert(message);
      return false;
    }
  } catch (error) {
    console.error('Error:', error);
    alert('Error adding domain to blacklist');
    return false;
  }
}

// Delete domain from blacklist
async function deleteFromBlacklist(id) {
  if (!confirm('Are you sure you want to remove this domain from the blocklist?')) {
    return;
  }

  try {
    const response = await fetch('/api/blacklist/delete', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: `id=${id}`
    });

    if (response.ok) {
      const result = await response.json();
      if (result === 1) {
        loadBlacklist(); // Refresh the list
      } else {
        alert('Failed to remove domain from blacklist');
      }
    } else {
      const error = await response.json();
      alert(`Error removing domain: ${error.message}`);
    }
  } catch (error) {
    console.error('Error:', error);
    alert('Error removing domain from blacklist');
  }
}

// Initialize blacklist functionality only on dashboard page
if (document.querySelector('.custom-list-panel')) {
  // Load blacklist on page load
  document.addEventListener('DOMContentLoaded', loadBlacklist);

  // Add event listener to add-to-blacklist-btn
  document.getElementById('add-to-blacklist-btn').addEventListener('click', async function() {
    const domainInput = document.getElementById('block-domain-input');
    const domain = domainInput.value.trim();
    
    if (!domain) {
      alert('Please enter a domain');
      return;
    }

    const success = await addToBlacklist(domain);
    if (success) {
      domainInput.value = ''; // Clear input
    }
  });
}
