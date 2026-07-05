(function renderReviewDesk() {
  const data = window.springReviewData;

  const scorePanel = document.getElementById("score-panel");
  const priorityGrid = document.getElementById("priority-grid");
  const strengthList = document.getElementById("strength-list");
  const recurringList = document.getElementById("recurring-list");
  const mentalModels = document.getElementById("mental-models");
  const roundsList = document.getElementById("rounds-list");

  const recurringTags = data.rounds
    .flatMap((round) => round.corrections.flatMap((item) => item.tags))
    .reduce((accumulator, tag) => {
      accumulator[tag] = (accumulator[tag] || 0) + 1;
      return accumulator;
    }, {});

  const recurringSummary = Object.entries(recurringTags)
    .sort((left, right) => right[1] - left[1])
    .slice(0, 6)
    .map(([tag, count]) => ({
      tag,
      count,
      detail: recurringTagDetail(tag)
    }));

  scorePanel.innerHTML = `
    <p class="micro-label">Current Snapshot</p>
    <p class="score-copy">${data.learnerSnapshot.profile}</p>
    <div class="score-stack">
      ${data.rounds
        .map(
          (round) => `
            <div class="score-block">
              <span class="score-kicker">${round.label}</span>
              <span class="score-value">${round.score}</span>
              <p class="score-copy">${round.headline}</p>
            </div>
          `
        )
        .join("")}
    </div>
    <p class="meta-note">${data.learnerSnapshot.currentTheme}</p>
  `;

  priorityGrid.innerHTML = data.priorityFocus
    .map(
      (item, index) => `
        <article class="card ${index === 0 ? "accented" : ""}">
          <div class="card-head">
            <h3>${item.title}</h3>
            <span class="tiny-stat">focus ${String(index + 1).padStart(2, "0")}</span>
          </div>
          <p class="body-copy" style="margin-top: 10px;">${item.why}</p>
          <span class="callout">${item.upgrade}</span>
        </article>
      `
    )
    .join("");

  strengthList.innerHTML = data.keepDoing
    .map(
      (item) => `
        <article class="list-card">
          <h3>${item.title}</h3>
          <p class="list-copy" style="margin-top: 8px;">${item.detail}</p>
        </article>
      `
    )
    .join("");

  recurringList.innerHTML = recurringSummary
    .map(
      (item) => `
        <article class="list-card">
          <div class="card-head">
            <h3>${formatTag(item.tag)}</h3>
            <span class="tiny-stat">${item.count} rounds</span>
          </div>
          <p class="list-copy" style="margin-top: 8px;">${item.detail}</p>
        </article>
      `
    )
    .join("");

  mentalModels.innerHTML = data.mentalModels
    .map(
      (item) => `
        <article class="card">
          <h3>${item.title}</h3>
          <p class="body-copy" style="margin-top: 10px;">${item.summary}</p>
          <div class="round-grid" style="margin-top: 16px;">
            <div>
              <p class="micro-label">Remember</p>
              <ul class="bullet-list">
                ${item.remember.map((bullet) => `<li>${bullet}</li>`).join("")}
              </ul>
            </div>
            <div>
              <p class="micro-label">Watch For</p>
              <ul class="bullet-list">
                ${item.watchFor.map((bullet) => `<li>${bullet}</li>`).join("")}
              </ul>
            </div>
          </div>
        </article>
      `
    )
    .join("");

  roundsList.innerHTML = data.rounds
    .map(
      (round) => `
        <article class="round-card" id="${round.id}">
          <div class="round-topline">
            <div>
              <p class="section-label">${round.label}</p>
              <h2>${round.headline}</h2>
            </div>
            <div class="round-score">
              <span class="micro-label">Score</span>
              <strong>${round.score}</strong>
            </div>
          </div>

          <p class="round-copy">${round.headline}</p>

          <div class="round-grid">
            <section class="list-card">
              <p class="micro-label">Strong Signals</p>
              <ul class="bullet-list">
                ${round.strengths.map((bullet) => `<li>${bullet}</li>`).join("")}
              </ul>
            </section>

            <section class="list-card">
              <p class="micro-label">Misses To Revisit</p>
              <ul class="bullet-list">
                ${round.recurringMisses.map((bullet) => `<li>${bullet}</li>`).join("")}
              </ul>
            </section>
          </div>

          <div class="correction-grid">
            ${round.corrections
              .map(
                (item) => `
                  <article class="correction-card">
                    <h4>${item.title}</h4>
                    <p class="list-copy"><strong>Your answer:</strong> ${item.yourTake}</p>
                    <p class="list-copy" style="margin-top: 10px;"><strong>Correction:</strong> ${item.correction}</p>
                    <span class="callout">${item.takeaway}</span>
                    <div class="tag-row">
                      ${item.tags.map((tag) => `<span class="tag-chip">${formatTag(tag)}</span>`).join("")}
                    </div>
                  </article>
                `
              )
              .join("")}
          </div>

          <p class="micro-label" style="margin-top: 18px;">Next Steps</p>
          <ul class="bullet-list">
            ${round.nextSteps.map((bullet) => `<li>${bullet}</li>`).join("")}
          </ul>
        </article>
      `
    )
    .join("");

  function formatTag(tag) {
    return tag
      .split("-")
      .join(" ")
      .replace(/\b\w/g, (character) => character.toUpperCase());
  }

  function recurringTagDetail(tag) {
    const detailMap = {
      annotations:
        "You know the labels, but you still need a firmer sense of which annotations register beans, which mark persistence models, and which drive infrastructure behavior.",
      beans:
        "Bean ownership, lifecycle, and creation flow are still recurring weak points. Keep asking who creates the object and whether Spring owns it.",
      boot:
        "Boot is still feeling a bit magical. Keep tying behavior back to dependencies, properties, existing beans, and conditions.",
      container:
        "The main shift is from writing objects yourself to letting the container own wiring and enhancement.",
      transactions:
        "You understand atomicity, but the proxy boundary and invocation path still need repetition."
    };

    return detailMap[tag] || "This concept has shown up repeatedly and is worth revisiting before the next round.";
  }
})();
