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
        domainSpan.textContent = item.domainName;
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

    if (response.ok) {
      const result = await response.json();
      if (result === 1) {
        alert('Domain added to blacklist successfully');
        loadBlacklist(); // Refresh the list
        return true;
      } else {
        alert('Failed to add domain to blacklist');
      }
    } else {
      alert('Error adding domain to blacklist');
    }
  } catch (error) {
    console.error('Error:', error);
    alert('Error adding domain to blacklist');
  }
  return false;
}

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
