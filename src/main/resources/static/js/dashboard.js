// Tab switching logic
const tabs = document.querySelectorAll('.tab');
const blockDomainInput = document.getElementById('block-domain-input');
const allowDomainInput = document.getElementById('allow-domain-input');
const addToBlacklistBtn = document.getElementById('add-to-blacklist-btn');
const addToWhitelistBtn = document.getElementById('add-to-whitelist-btn');
let currentList = 'blocklist';

// Initialize with blocklist
loadBlacklist();

tabs.forEach(tab => {
  tab.addEventListener('click', () => {
    // Update active state
    tabs.forEach(t => t.classList.remove('active'));
    tab.classList.add('active');
    
    // Update current list and load appropriate data
    currentList = tab.dataset.list;
    if (currentList === 'blocklist') {
      blockDomainInput.style.display = '';
      addToBlacklistBtn.style.display = '';
      allowDomainInput.style.display = 'none';
      addToWhitelistBtn.style.display = 'none';
      loadBlacklist();
    } else {
      blockDomainInput.style.display = 'none';
      addToBlacklistBtn.style.display = 'none';
      allowDomainInput.style.display = '';
      addToWhitelistBtn.style.display = '';
      loadWhitelist();
    }
  });
});

// Handle domain addition
addToBlacklistBtn.addEventListener('click', async function() {
  const domain = blockDomainInput.value.trim();
  
  if (!domain) {
    alert('Please enter a domain');
    return;
  }

  let success = await addToBlacklist(domain);

  if (success) {
    blockDomainInput.value = ''; // Clear input
  }
});

addToWhitelistBtn.addEventListener('click', async function() {
  const domain = allowDomainInput.value.trim();
  
  if (!domain) {
    alert('Please enter a domain');
    return;
  }

  let success = await addToWhitelist(domain);

  if (success) {
    allowDomainInput.value = ''; // Clear input
  }
});

// Handle domain check form submission
document.querySelector('.url-form').addEventListener('submit', async function(e) {
  e.preventDefault();
  const domain = document.querySelector('.url-input').value.trim();
  
  if (!domain) {
    alert('Please enter a domain');
    return;
  }

  try {
    const response = await fetch(`/api/domain/score?domain=${encodeURIComponent(domain)}`, {
      method: 'GET'
    });

    if (response.ok) {
      const result = await response.json();
      const resultContainer = document.getElementById('result-container');
      
      // Clear previous results
      resultContainer.innerHTML = '';
      
      // Create result display
      const resultDiv = document.createElement('div');
      resultDiv.className = `result-container result-${result.riskLevel}`;
      
      // Create content
      let content = `
        <div class="result-header">
          <h3>Risk Level: ${result.riskLevel.replace('_', ' ')}</h3>
          <button class="dismiss-btn" aria-label="Dismiss results">×</button>
        </div>
        <p>Total Score: ${result.totalScore}/100</p>
        <div class="score-details">
          <div class="score-item">
            <span>List Score:</span>
            <span>${result.listScore}</span>
          </div>
          <div class="score-item">
            <span>Structure Score:</span>
            <span>${result.structureScore}</span>
          </div>
          <div class="score-item">
            <span>SSL Score:</span>
            <span>${result.sslScore}</span>
          </div>
          <div class="score-item">
            <span>WHOIS Score:</span>
            <span>${result.whoisScore}</span>
          </div>
        </div>
      `;
      
      resultDiv.innerHTML = content;
      resultContainer.appendChild(resultDiv);

      // Add click handler for dismiss button
      resultDiv.querySelector('.dismiss-btn').addEventListener('click', function() {
        resultDiv.remove();
      });
    } else {
      alert('Error checking domain');
    }
  } catch (error) {
    console.error('Error:', error);
    alert('Error checking domain');
  }
});
