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
        domainSpan.textContent = item.domainName;
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
      const result = await response.json();
      if (result === 1) {
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
