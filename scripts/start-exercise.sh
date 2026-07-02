#!/usr/bin/env bash
#
# Start a new exercise: create a dated branch and a pre-filled note stub.
#
# Usage:
#   ./scripts/start-exercise.sh <EXERCISE-ID> <short-slug>
#
# Example:
#   ./scripts/start-exercise.sh JAVA-03 generics-validator
#     -> branch:  exercise/2026-07-02-generics-validator
#     -> note:    notes/2026-07-02-generics-validator.md (seeded with JAVA-03)
#
set -euo pipefail

if [[ $# -lt 2 ]]; then
  echo "Usage: $0 <EXERCISE-ID> <short-slug>" >&2
  echo "Example: $0 JAVA-03 generics-validator" >&2
  exit 1
fi

EXERCISE_ID="$1"
SLUG="$2"
DATE="$(date +%Y-%m-%d)"

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$REPO_ROOT"

BRANCH="exercise/${DATE}-${SLUG}"
NOTE="notes/${DATE}-${SLUG}.md"
TEMPLATE="notes/_template.md"

if git rev-parse --verify --quiet "$BRANCH" >/dev/null; then
  echo "Branch $BRANCH already exists; checking it out." >&2
  git checkout "$BRANCH"
else
  git checkout -b "$BRANCH"
fi

if [[ -e "$NOTE" ]]; then
  echo "Note $NOTE already exists; leaving it untouched." >&2
else
  if [[ -f "$TEMPLATE" ]]; then
    cp "$TEMPLATE" "$NOTE"
  else
    printf '# %s - %s\n' "$DATE" "$SLUG" > "$NOTE"
  fi
  # Seed the header and exercise id so the note is ready to write into.
  # (BSD/macOS sed and GNU sed both accept -i with an explicit backup suffix,
  #  which we then remove to stay portable.)
  sed -i.bak \
    -e "1s|.*|# ${DATE} - ${SLUG}|" \
    -e "s|- EXERCISE-ID: short title|- ${EXERCISE_ID}: |" \
    "$NOTE"
  rm -f "${NOTE}.bak"
  echo "Created $NOTE"
fi

echo
echo "Ready. You are on $BRANCH."
echo "Next: implement ${EXERCISE_ID} under src/main/java/com/nick/javafundamentals/,"
echo "add tests, then run:  ./mvnw verify"
