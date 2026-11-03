# CryptoFixYup — Public Engineering Ecosystem

This page groups selected public repositories by their current role. It describes a **portfolio of related projects**, not a deployed or end-to-end integrated platform. Each repository remains independently versioned and is the source of truth for its code and tests.

## 1. Deterministic authorization and agent boundaries

- [Alpha Sentinel](https://github.com/cryptofixyup/alpha-sentinel) — deterministic policy and authorization controls for autonomous agents. The repository explicitly distinguishes implemented components and CI evidence from production signing, key custody and end-to-end live execution that are not yet demonstrated.
- [Policy Activation Kernel](https://github.com/cryptofixyup/policy-activation-kernel) — dependency-free reference kernel for policy activation, semantic validation, deterministic execution and transition vectors.

## 2. Data integrity and market research

- [pelican-rs](https://github.com/cryptofixyup/pelican-rs) — Rust telemetry decoder with length, version, CRC32-C, numeric and semantic validation. CRC detects accidental corruption; cryptographic authentication is a separate layer.
- [market-monitor-rs](https://github.com/cryptofixyup/market-monitor-rs) — early-stage Rust market-monitoring and signal components with fail-closed handling for invalid prices.
- [AI Hedge Fund](https://github.com/cryptofixyup/ai-hedge-fund) — educational multi-agent investment-research proof of concept. It does not execute trades and is not an investment service.

## 3. Application and vertical experiments

- [Autonomous Operations System](https://github.com/cryptofixyup/piotr-kozlowski-autonomous-system) — logistics operations scaffold with deterministic business constraints, policy gates and an AI orchestration boundary.
- [Crypto Safety Scanner](https://github.com/cryptofixyup/DeFi-Scam-Scanner) — Android-first wallet/token risk-scanning product foundation. Its README states the scan endpoint is readiness-only until a real risk engine is connected; it does not fabricate risk scores.

## 4. Research and evaluation

- [OBLITERATUS](https://github.com/cryptofixyup/OBLITERATUS) — public workspace related to LLM refusal-mechanism research. Individual contribution scope should be established from commit history rather than inferred from repository ownership.

## 5. Shared engineering principles

- Evidence before confidence.
- Reproducibility before conclusions.
- AI may propose; deterministic controls decide.
- Untrusted input must not acquire authority.
- Approval, execution and verification are separate stages.
- A passing test proves only the behaviour covered by that test.

## 6. Current integration boundary

These projects are not represented as a single running product. The intended direction is to standardize evidence and policy interfaces while preserving domain boundaries and independent release gates. No live trading, production wallet signing, clinical deployment, or end-to-end security guarantee is claimed here.

For bounded engineering engagements, see [services.md](services.md).