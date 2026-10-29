# Piotr Kozłowski

**AI security & systems engineering · Founder, CryptoFixYup**

I build software that puts explicit controls around AI-generated decisions. My focus is deterministic authorization, lifecycle state machines, cryptographic integrity, and reproducible engineering evidence.

**Rust · TypeScript / Node.js · Python · C++ / JNI · GitHub Actions**

## Selected work

| Project | What it contains |
| --- | --- |
| [Alpha Sentinel](https://github.com/cryptofixyup/alpha-sentinel) | AI-agent authorization research: policy checks, transaction-bound approvals, simulation boundaries, lifecycle handling, and regression tests. |
| [Autonomous Operations System](https://github.com/cryptofixyup/piotr-kozlowski-autonomous-system) | Logistics-focused scaffold with deterministic business constraints, an AI orchestration boundary, and operational APIs. |
| [market-monitor-rs](https://github.com/cryptofixyup/market-monitor-rs) | Rust market-signal components with validation for invalid prices and a CI workflow for formatting, compilation, linting, and tests. |
| [pelican-rs](https://github.com/cryptofixyup/pelican-rs) | Rust telemetry wire-decoder components with length, version, checksum, and semantic validation. |
| [JNI Benchmark Harness (sample)](samples/jev-jni-benchmark-harness/) | C++17/JNI bridge, Java workload adapter, p50/p95 latency reporting, and a CI smoke build. Uses a synthetic workload until the actual JEV source/build is available; no JEV performance claim is made. |

## Engineering approach

- Keep model reasoning separate from execution authority.
- Make state transitions and failure modes explicit.
- Turn discovered defects into regression tests.
- Tie results to the exact code, fixtures, and environment evaluated.
- Distinguish prototypes, passing tests, and demonstrated production behavior.

Alpha Sentinel is under active development. Its README documents implemented components, historical CI evidence, and integration work still to be demonstrated.

## Paid engineering pilots

I take on small, fixed-scope tasks with written acceptance criteria and verifiable results. Introductory pilot prices are in GBP; final scope, schedule, and payment terms are agreed before work begins.

| Service | Pilot price | Scope and acceptance criterion |
| --- | ---: | --- |
| [CI Pipeline Rescue](services.md#1-ci-pipeline-rescue--introductory-pilot-250) | £250 | One failing or flaky GitHub Actions workflow; focused patch, regression check where feasible, and a recorded result. |
| [AI Agent Authorization Integration](services.md#2-ai-agent-authorization-integration--introductory-pilot-650) | £650 | One existing agent workflow and one action boundary; explicit policy/approval handling, structured decision logs, and agreed regression tests. |
| [Telemetry / Data-Integrity Repair](services.md#3-telemetry--data-integrity-repair--introductory-pilot-350) | £350 | One parser or ingestion boundary; invalid-input fixtures, focused fix, regression tests, and CI evidence. |

**Full scope and boundaries:** [services.md](services.md).

### Evidence and limits

- [Alpha Sentinel](https://github.com/cryptofixyup/alpha-sentinel) documents its current implementation, test evidence, and production capabilities not yet demonstrated.
- [market-monitor-rs](https://github.com/cryptofixyup/market-monitor-rs) contains Rust market-signal components and CI checks.
- [pelican-rs](https://github.com/cryptofixyup/pelican-rs) contains telemetry decoding and validation components.
- A test passing in a repository is evidence for that test and commit; it is not a universal production-security guarantee.

## Repository Security & CI Evidence Review

For teams that specifically need a read-only review, I also offer a **£150 introductory review** of one GitHub repository at an agreed commit, covering up to six GitHub Actions workflows. The deliverable is a concise evidence memo with exact-SHA source references, prioritized findings, CI coverage mapping, limitations, and actionable repair recommendations.

**Sample:** [Independent source-only GitHub Actions review of `pallets/click`](https://github.com/cryptofixyup/cryptofixyup/blob/main/samples/github-actions-review-pallets-click.md). This public-source demonstration was not commissioned by or endorsed by the Pallets project.

The review is read-only. It does not include penetration testing, production credentials, deployment, compliance certification, guaranteed vulnerability discovery, or a claim that source inspection proves runtime security.

## Contact

Email [kpiotr210@gmail.com](mailto:kpiotr210@gmail.com) with the service name, repository URL, and the specific failure or workflow. Do not send secrets or credentials. No work begins until scope and payment terms are agreed in writing.

[All public repositories](https://github.com/cryptofixyup?tab=repositories)
