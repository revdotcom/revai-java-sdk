# Repo Readiness Audit — revai-java-sdk

**Repo:** `revdotcom/revai-java-sdk`
**Default branch:** `develop`
**Audited:** 2026-05-12
**Mode:** repo mode (single root, Maven/Java SDK, not a workspace)
**Stack detected:** JVM / Java (Maven) — `pom.xml`, Java 8/11/17 build matrix. 108 tracked `.java` files (81 main + 27 test).
**Runtime data stores:** none (this is a client SDK). External dependency: Rev AI HTTP/WebSocket API (`docs.rev.ai`).

## Headline

**Band: Early** — 2/23 applicable items PASS (8.7%); 8 Tier-1 FAILs.

The repo is a clean, narrowly-scoped client SDK with a working test suite and active CI matrix, but it ships **zero** agent-readiness artifacts: no `CLAUDE.md`/`AGENTS.md`, no `.claude/`, no contributing guide, no architecture doc, no environment template, no permissions/hooks/telemetry. The README is solid as user documentation but is not a substitute for agent-onboarding scaffolding, and the build workflow has been red on `develop` since 2026-03-04 (3 consecutive merge runs failed). At ~108 source files the repo is above the "small" annotation threshold, so the standard band applies as-is.

No Tier-1 profile annotation (both knowledge and tooling clusters are weak — no imbalance to highlight).

## Checklist

### Tier 1 — Foundation

| # | Item | Status | Evidence |
|---|------|--------|----------|
| 1 | AGENTS.md / CLAUDE.md | FAIL | No `AGENTS.md`, `CLAUDE.md`, `.cursorrules`, `.windsurfrules`, or `GEMINI.md` anywhere in the tree. |
| 2 | Per-language best practices checked in | FAIL | No `STYLE_GUIDE.md`, `CODING_STANDARDS.md`, `docs/style*`, or `.claude/tips/`. Java style guidance is a single paragraph in `README.md` pointing IntelliJ users at the `google-java-format` plugin (lines 326-334). Java is the only primary language — gap is total. |
| 3 | Architecture + operational surface | PARTIAL | `README.md` is rich on **usage** (HTTP API client, streaming, custom vocab, captions, languages) but covers no operational surface — no environments doc, no runtime hosting note (N/A: library), no observability pointers, no link to where IaC lives. Architecture-as-a-component-diagram is implicit; "where do I go if X breaks?" is unanswered. The release path is touched briefly ("Create GitHub tag → release → action publishes to Maven Central" in `README.md:345-349`). |
| 4 | DB schema reference | N/A | Client SDK; no database. |
| 5 | CONTRIBUTING.md (PR + commit conventions) | PARTIAL | No `CONTRIBUTING.md` / `docs/contributing*`. `README.md` "For Rev AI Java SDK Developers" section (lines 324-349) covers IDE setup and the release path; PR and commit-message conventions are not documented. PRs in practice use `<TICKET>:<title>` (REVAI-, AISDK-, DOCS-) but the convention is implicit. |
| 6 | .env.example | PARTIAL | No `.env.example` / `.env.sample` / `.env.template`. The only runtime env var (`REVAI_ACCESS_TOKEN` for integration tests) is documented in `README.md:338-343` as a setup step rather than an enumerated keys-only file. |
| 7 | settings.json with pre-approved permissions | FAIL | No `.claude/` directory. No `.cursor/rules`. |
| 8 | Skills directory | FAIL | No `.claude/skills/`, `.agents/skills/`, or `skills/`. |
| 9 | Skill-creator skill | FAIL | None present (no skills infra at all). |
| 10 | Hooks for deterministic checks | FAIL | No `.claude/settings.json` hooks, no `.githooks/`, no `husky`, no `.pre-commit-config.yaml`. Formatting compliance is an IntelliJ-only honor-system step (README.md:331-334). |
| 11 | Hooks for agent friction | FAIL | No `PreToolUse` hooks of any kind. |
| 12 | Tool-call telemetry | FAIL | No `CLAUDE_CODE_ENABLE_TELEMETRY`, no OTLP config, no telemetry references. |

### Tier 2 — DX & Speed

| # | Item | Status | Evidence |
|---|------|--------|----------|
| 13 | Single-command local dev setup | PARTIAL | `mvn install -DskipTests=true` (`README.md:22`) is one command for build/install; `mvn package` runs unit tests; `mvn verify` runs integration tests. No `Makefile`/`justfile`/`docker-compose.yml`. Adequate for a Maven library; not best-in-class. |
| 14 | Fast linting (<30s) | FAIL | No lint configuration: no `.editorconfig`, no Checkstyle/SpotBugs/Error Prone/PMD config, no `google-java-format` plugin wired into Maven (it's an IDE-only manual step per README.md:331-334). CI does not run a separate lint job. |
| 15 | Fast formatting (<20s) | PARTIAL | Formatter convention exists (`google-java-format`, README.md:328-334) but enforcement is honor-system — no pre-commit hook, no Maven `fmt-maven-plugin` or `spotless-maven-plugin`, no CI check. |
| 16 | Fast compilation / type checking (<45s) | PASS | `maven-compiler-plugin` 3.8.0 with Java 1.8 source/target (`pom.xml:117-120, 34-35`). CI runs `mvn -B package` on Java 8/11/17 matrix (`.github/workflows/build_and_test.yml`). Compile step well under 45s in the matrix (Phase 2: total job runs ~3.4 min across all 3 JVMs combined, compile sub-step very fast). |
| 17 | Reliable tests | PASS | Real JUnit 4 + Mockito + AssertJ + MockWebServer suite. 27 test files split into `unit/` (run by `surefire`) and `integration/` (run by `failsafe` on `mvn verify`, gated by `REVAI_ACCESS_TOKEN`). Run instructions in `README.md:336-343`. No large skipped blocks observed. Note that on Phase 2 the last 3 merge-runs on `develop` failed at the integration-test stage — see item 18 finding below. |
| 18 | Fast CI | EXTERNAL (validated, broken) | `.github/workflows/build_and_test.yml` triggers on push to all branches. Last 7 build runs: 6 failures / 1 success. **Build workflow has been red on `develop` for 3 consecutive merge events** (2026-03-04, 2026-03-05, 2026-03-12), median run 3.3 min. Failure surfaces at the failsafe integration-test step on Java 8/17 (Java 11 cancelled, Java 8 failed, Java 17 failed). This is a "recent stability incident" — see Phase 2 findings. |
| 19 | Branch previews | N/A | Library / SDK; no deployable preview surface. |

### Tier 3 — Advanced

| # | Item | Status | Evidence |
|---|------|--------|----------|
| 20 | Skills marketplace | FAIL | No `.claude/settings.json`. No marketplace references. |
| 21 | Specs as code | N/A | The SDK *consumes* Rev AI's HTTP/WebSocket API; the source-of-truth OpenAPI/spec lives outside this repo (at `docs.rev.ai`). No internal API surface to spec. |
| 22 | MCP server configs | FAIL | No `.mcp.json`. No MCP references. |
| 23 | Agentic code review | EXTERNAL (not present) | No `.cursor/`, `.coderabbit.yaml`, `.greptile*`, Bugbot config. No `claude.yml` workflow. Reviewers on the last 20 merged PRs are all human (`Greyvend`, `dmtrrk`, `eugenep-rev`, `kbridbur`, `amikofalvy`, `aaron-wilson-rev`, `kirillatrev`, etc.) — no `*[bot]` reviewers. CODEOWNERS (`@revdotcom/revai-devs`) routes review to a human team. |
| 24 | Deterministic PR guardrails | EXTERNAL (validated, partial) | **Firing on PRs:** CodeQL (org default-setup, `Analyze (java-kotlin)` + `Analyze (actions)` check-runs verified on PR #67 head and merge SHAs), GitHub Advanced Security secret scanning + push protection, `linkChecker` (lychee, `.github/workflows/links-fail-fast.yml`), `stale-branches.yml` + `stale.yml` (PR/branch hygiene). **Dependabot security updates enabled** at the repo level (5 open Dependabot alerts at audit time) and authoring PRs (`dependabot/maven/com.google.code.gson-gson-2.8.9`, `dependabot/maven/org.json-json-20231013` branches on remote, 4 dependabot PRs over repo lifetime). **Missing:** no `.github/dependabot.yml` so version-update PRs are not configured; no Semgrep, Chromatic, Percy, secret-scanner config-file, Lighthouse, Snyk. Stale PR/branch automation is in place. |
| 25 | Agents in the cloud | FAIL | No `claude.yml`, no Devin/Replit/Modal/Inspect config. |
| 26 | Slack + issue-tracker integration | EXTERNAL (strong) | **Ticket linkage: 20/20** of the last 20 merged PRs reference a Linear/Jira-style ticket (`REVAI-####`, `AISDK-###`, `DOCS-###`) in title and branch name. No PR template (`.github/PULL_REQUEST_TEMPLATE.md` absent) — the discipline is convention-driven. **Slack integration:** CI workflows post pass/fail to `#revai-alerts-prod` via `voxmedia/github-action-slack-notify-build@v1` (`build_and_test.yml:60-77`, `publish-maven.yml:42-60`). No Slack MCP / Linear MCP config. |

## External Validation Summary (Phase 2)

### CI timing (item 18)

| Workflow | Trigger | Last-7 runs | Median | Status |
|----------|---------|-------------|--------|--------|
| `build_and_test.yml` | push, all branches | 6 fail / 1 pass | 3.3 min | **Red on `develop`** for 3 consecutive merges (2026-03-04, -05, -12). Failures at the failsafe integration-test step. |
| `links-fail-fast.yml` | push + PR | 10/10 success | 0.25 min | Fast & green. |
| `stale-branches.yml` | schedule (M-F 14:04 UTC) | 9/10 success | 0.25 min (one outlier at 15 min, 2026-05-05) | Healthy. |
| `copyright-update.yml` | schedule (annual) | inactive (disabled by GitHub for inactivity) | n/a | Long-running by design — annual schedule. |
| `publish-maven.yml` | release-created | no runs visible in last window | n/a | Last activity tied to release cadence; not red. |
| `stale.yml` | schedule daily | disabled by inactivity | n/a | Configured but not currently running. |

Total per-merge gate (`build_and_test.yml` × 3 Java versions in matrix) runs in ~3.3 min — well under "Fast" (<5 min) when green. The matrix uses cached `~/.m2/repository` keyed on `pom.xml` hash. Note: action versions are mixed — `publish-maven.yml` still on `actions/checkout@v2` + `actions/setup-java@v1` (deprecated Node 16). Build workflow uses `v4`s.

**Recent stability incident:** `build_and_test.yml` has failed on `develop` 3 times in a row (2026-03-04 through 2026-03-12). The most recent failure surfaces in the failsafe (`mvn verify`) integration-test stage — i.e., when `INTEGRATION_TEST_ACCESS_TOKEN` runs against live Rev AI. Maven build itself is green; integration tests against the live API are failing. Worth confirming whether the integration-test token has rotated/expired or whether API contract has drifted.

### PR guardrails (item 24)

Sampled the last 20 merged PRs (`#67, #66, #65, #64, #63, #62, #61, #59, #57, #56, #55, #54, #53, #52, #50, #49, #47, #46, #45, #44` — full sample to 2022-05-09). Check-runs on the most recent active PR (`#67`) confirmed:

- `Analyze (java-kotlin)` — CodeQL — present on head + merge.
- `Analyze (actions)` — CodeQL actions analysis — present on merge.
- `linkChecker` — lychee — present on head + merge.
- `build (8)` / `build (11)` / `build (17)` — Maven build matrix — present on head.

No `claude`, `coderabbit`, `greptile`, `cursor`, `bugbot`, `sweep` check-runs found. No bot reviewers found in the reviews API across the 20 PRs sampled. **Undiscovered-locally guardrails (org/repo-level, not in `.github/`):** CodeQL default setup, GitHub secret scanning, Dependabot security alerts.

### Issue-tracker linkage (item 26)

20/20 recent merged PRs reference a ticket. Primary tracker: **Linear** (`REVAI-` prefix on recent PRs, `AISDK-` on 2022-era PRs — likely a tracker migration). Secondary: `DOCS-` (`jennywong2129`'s docs work). No `-2` / rework suffixes observed in the sample. No PR template in `.github/`.

### Cloud-agent invocation (item 25)

No Claude Code Action or equivalent workflow file. No invocations to report.

## Top 3 Next Actions

1. **Add `CLAUDE.md`** (item 1, blocking everything in Tier 1). A 30-60 line file covering: build commands (`mvn package`, `mvn verify` + token), code layout (`ai.rev.speechtotext` / `languageid` / `sentimentanalysis` / `topicextraction` / `helpers`), API client architecture (Retrofit + OkHttp + Gson), test conventions (`unit/` via surefire, `integration/` via failsafe, gated by `REVAI_ACCESS_TOKEN`), and a pointer to the external Rev AI API docs at `docs.rev.ai` would close the biggest gap.
2. **Fix the broken `build_and_test.yml` on `develop`** (item 18). The workflow has been red for 3 consecutive merges since 2026-03-04 — failsafe integration tests are failing on Java 8 and 17. Either the integration token is stale or the live API has drifted from the SDK's assumptions; without a green default branch, agents (and humans) can't tell what's "expected red" from what's "their break."
3. **Pick one of: `CONTRIBUTING.md` with PR/commit conventions + a `.env.example` listing `REVAI_ACCESS_TOKEN`** (items 5, 6), **OR formatting enforcement** (item 15 — wire `spotless-maven-plugin` with `google-java-format` into `mvn verify` so the IDE-only honor-system convention becomes deterministic). Both are small lifts that move multiple items at once.

## Residual Unknowns

- **Build red since 2026-03-04 — root cause unclear from the audit.** The failsafe step fails on Java 8 and 17 (Java 11 cancelled by the previous matrix-leg failure). Could be integration-test token rotation, live-API drift, or test-resource breakage. Out of audit scope to fix; flagged because it materially affects readiness — agents can't trust CI signals.
- **`publish-maven.yml` uses deprecated action versions** (`actions/checkout@v2`, `actions/setup-java@v1`). Won't break today but will when GitHub finishes its Node 20→24 migration (June 2026). Side-finding from CI log inspection.
- **No `.github/dependabot.yml`** despite Dependabot **security** updates being enabled at repo level. Version-bump PRs aren't being opened automatically; only security-alert-driven PRs are. A 10-line config would close the gap.
- **Tracker prefix transition (`AISDK-` → `REVAI-`).** Linear/Jira tracker rename happened sometime between 2022 and 2023-12; not documented anywhere in the repo. Minor — agents would discover it from recent PRs — but a one-line note in `CLAUDE.md` would save the discovery step.
- **README hardcoded version drift risk** (`README.md:16` says `2.2.0`; `pom.xml:8` says `2.6.0`). Doc-vs-source mismatch; consumers copying the README's dependency block would land on a stale version.
