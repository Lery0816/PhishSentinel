// Load and display whitelist
async function loadWhitelist() {
  try {
    const response = await fetch('/api/whitelist/list', {
      method: 'POST'
    });

    if (response.ok) {
      const whitelist = await response.json();
      const listCard = document.querySelector('.list-card');
      listCard.innerHTML = ''; // Clear existing items

      whitelist.forEach(item => {
        const domainSpan = document.createElement('div');
        domainSpan.className = 'label allow';
        domainSpan.dataset.id = item.id; // Store the ID in the DOM element
        
        const domainText = document.createElement('span');
        domainText.textContent = item.domainName;
        domainSpan.appendChild(domainText);

        const deleteBtn = document.createElement('button');
        deleteBtn.className = 'delete-btn';
        deleteBtn.innerHTML = '×';
        deleteBtn.title = 'Remove from allowlist';
        deleteBtn.onclick = (e) => {
          const id = e.currentTarget.parentElement.dataset.id;
          deleteFromWhitelist(id);
        };
        domainSpan.appendChild(deleteBtn);

        listCard.appendChild(domainSpan);
      });
    } else {
      console.error('Failed to load whitelist');
    }
  } catch (error) {
    console.error('Error loading whitelist:', error);
  }
}

// Add domain to whitelist
async function addToWhitelist(domain) {
  try {
    const response = await fetch('/api/whitelist/add', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: `domainname=${encodeURIComponent(domain)}`
    });

    if (response.ok) {
      const whiteList = await response.json();
      if (whiteList && whiteList.id) {
        alert('Domain added to whitelist successfully');
        loadWhitelist(); // Refresh the list
        return true;
      } else {
        alert('Failed to add domain to whitelist');
      }
    } else {
      alert('Error adding domain to whitelist');
    }
  } catch (error) {
    console.error('Error:', error);
    alert('Error adding domain to whitelist');
  }
  return false;
}

// Delete domain from whitelist
async function deleteFromWhitelist(id) {
  if (!confirm('Are you sure you want to remove this domain from the allowlist?')) {
    return;
  }

  try {
    const response = await fetch('/api/whitelist/delete', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: `id=${id}`
    });

    if (response.ok) {
      const result = await response.json();
      if (result === 1) {
        loadWhitelist(); // Refresh the list
      } else {
        alert('Failed to remove domain from whitelist');
      }
    } else {
      const error = await response.json();
      alert(`Error removing domain: ${error.message}`);
    }
  } catch (error) {
    console.error('Error:', error);
    alert('Error removing domain from whitelist');
  }
}

// Initialize whitelist functionality only on dashboard page
if (document.querySelector('.custom-list-panel')) {
  // Load whitelist on page load
  document.addEventListener('DOMContentLoaded', loadWhitelist);

  // Add event listener to add-to-whitelist-btn
  document.getElementById('add-to-whitelist-btn').addEventListener('click', async function() {
    const domainInput = document.getElementById('allow-domain-input');
    const domain = domainInput.value.trim();
    
    if (!domain) {
      alert('Please enter a domain');
      return;
    }

    const success = await addToWhitelist(domain);
    if (success) {
      domainInput.value = ''; // Clear input
    }
  });
}
