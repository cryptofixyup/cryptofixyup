# Fixed-scope engineering services

Piotr Kozłowski · CryptoFixYup · [GitHub profile](https://github.com/cryptofixyup)

I take on small, bounded engineering tasks where success can be checked against code, tests, and CI output. Every engagement starts with a written scope and acceptance criteria. I do not claim production guarantees or results that have not been verified.

## 1. CI Pipeline Rescue — introductory pilot £250

**For:** a small engineering team with one failing, flaky, or incorrectly configured GitHub Actions workflow.

**Scope:** one repository, one workflow, and one primary failure mode.

**Deliverables**
- Reproduction steps and the relevant failing log evidence.
- A focused patch in a branch or pull request.
- A regression check where feasible.
- A final run result and a short list of any remaining limitations.

**Acceptance criteria:** the agreed workflow passes on the agreed commit, or the work is stopped with the failure reproduced and the remaining blocker documented if an external dependency prevents a pass.

**Not included:** broad repository rewrites, cloud migration, paid third-party services, or production access not explicitly agreed.

## 2. AI Agent Authorization Integration — introductory pilot £650

**For:** a team with an existing tool-using agent that needs a bounded policy or approval gate before a consequential action.

**Scope:** one agent workflow and one action boundary, agreed before work begins.

**Deliverables**
- Explicit allow/deny rules for the agreed actions.
- A human-approval gate for the agreed high-risk action, if required by the scope.
- Structured decision logging that records the policy outcome without exposing secrets.
- Regression tests for allowed, denied, malformed, and failure cases relevant to the integration.
- A concise verification note linking claims to tests and code.

**Acceptance criteria:** the agreed test cases pass, including fail-closed behavior for the specified failure cases.

**Boundaries:** this is a scoped implementation, not a penetration test, compliance certification, formal security audit, production key-custody guarantee, or claim that an agent is safe in every environment.

**Relevant public work:** [Alpha Sentinel](https://github.com/cryptofixyup/alpha-sentinel). Its README distinguishes implemented components and historical CI evidence from production capabilities not yet demonstrated.

## 3. Telemetry / Data-Integrity Repair — introductory pilot £350

**For:** a team whose parser or telemetry pipeline needs stronger validation of malformed, truncated, versioned, or checksum-protected input.

**Scope:** one parser or ingestion boundary in an agreed repository.

**Deliverables**
- Reproducible examples for the agreed invalid-input cases.
- A focused fix and regression tests.
- CI evidence for the changed tests.
- A short note stating what was tested and what remains outside scope.

**Acceptance criteria:** the agreed valid fixtures continue to pass, the agreed invalid fixtures are rejected or handled according to the specified contract, and the relevant test suite passes.

**Relevant public work:** [pelican-rs](https://github.com/cryptofixyup/pelican-rs) and [market-monitor-rs](https://github.com/cryptofixyup/market-monitor-rs).

## Commercial terms

- Prices are introductory fixed-scope pilot prices in GBP, not a claim about market-wide rates.
- The exact scope, repository/commit, acceptance tests, schedule, and payment terms must be agreed in writing before work starts.
- No production credentials are needed for initial scoping.
- If the repository or failure cannot be accessed or reproduced, I will state the blocker rather than claim completion.
- No work begins until both sides agree to the scope and payment terms.

## Contact

Email [kpiotr210@gmail.com](mailto:kpiotr210@gmail.com) with the service number, repository URL, and the concrete failure or workflow you want addressed. Do not send secrets or credentials.
