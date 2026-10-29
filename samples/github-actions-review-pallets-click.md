# Sample GitHub Actions Review — `pallets/click`

**Type:** Independent public-source demonstration; not commissioned by or endorsed by the Pallets project  
**Reviewed commit:** [`2247b35ea1c47c727d7a06e51fa280e12a863ff6`](https://github.com/pallets/click/commit/2247b35ea1c47c727d7a06e51fa280e12a863ff6)  
**Review date:** 2026-10-29  
**Scope:** Six workflow files under `.github/workflows/` at the commit above. Read-only source review; no credentials, live environment, or repository settings accessed.

## Executive result

The reviewed workflows show several strong controls: workflow-level permissions are set to `{}`, write permissions are scoped to jobs that need them, third-party actions are pinned to full commit SHAs, and checkout steps disable persisted credentials. The release workflow separates build, draft-release creation, and PyPI publishing into distinct jobs.

One low-severity coverage gap was identified in the path filter for the `zizmor` workflow. A separate release-configuration check remains unverified because it depends on GitHub and PyPI settings that are not visible in repository workflow files.

## Finding F-01 — `.yml` workflow changes are not covered by the security-scan path filter

**Severity:** Low  
**Evidence:** [`zizmor.yaml` at the reviewed commit](https://github.com/pallets/click/blob/2247b35ea1c47c727d7a06e51fa280e12a863ff6/.github/workflows/zizmor.yaml), `on.pull_request.paths` and `on.push.paths` use `"**/*.yaml?"`.

GitHub's path-pattern syntax makes `?` optional for the preceding character. Consequently, this pattern matches `.yaml` (and the unusual `.yam` suffix), but does not match the common `.yml` extension. If a workflow is added or renamed to `.yml`, a change limited to that file will not trigger this security-analysis workflow through the configured path filters.

**Impact:** A future `.yml` workflow could bypass this particular automated `zizmor` run on pull request and branch push. This is a coverage gap, not evidence of an existing exploitable vulnerability.

**Recommended fix:** Replace the filter with explicit patterns, for example:

```yaml
paths:
  - '**/*.yaml'
  - '**/*.yml'
```

Apply the same patterns to both `pull_request` and `push`, then verify by opening a test change to a `.yml` file and confirming that the workflow starts.

## Verification item V-01 — Release and PyPI trust configuration

**Status:** Not verifiable from repository source alone.  
**Evidence:** [`publish.yaml` at the reviewed commit](https://github.com/pallets/click/blob/2247b35ea1c47c727d7a06e51fa280e12a863ff6/.github/workflows/publish.yaml).

The workflow triggers on tag pushes, grants `id-token: write` only to the `publish-pypi` job, and assigns that job to the `publish` environment. These are useful scope boundaries. This source review cannot establish whether the GitHub environment has the intended protection rules, whether PyPI trusted publishing is configured to the exact repository/workflow/environment, or whether tag creation is restricted appropriately.

**Recommended verification:** Confirm the `publish` environment's protection and deployment branch/tag rules, verify the PyPI trusted-publisher identity, and review repository tag rulesets. No misconfiguration is asserted without access to those settings.

## Controls observed

- All six reviewed workflow files set workflow-level `permissions: {}`.
- The issue-locking workflow grants only `issues`, `pull-requests`, and `discussions` write permissions to its locking job.
- The release workflow grants `contents: write` only to the draft-release job and `id-token: write` only to the PyPI publishing job.
- Referenced third-party actions are pinned to full commit SHAs in the reviewed files.
- Checkout steps set `persist-credentials: false`.
- A dedicated `zizmor` workflow exists for workflow-file changes.
- Test and pre-commit workflows cover pull requests and pushes to `main` / `stable`; the test workflow intentionally ignores documentation and README-only changes.

## Scope and limitations

This is a static review of workflow source at one commit. It does **not** verify actual workflow run results, branch protection or rulesets, GitHub environment settings, repository/organization secrets, runner configuration, PyPI account settings, or runtime security. It is not a penetration test, compliance certification, or guarantee that every issue has been found. Findings should be rechecked against the live configuration before making operational decisions.

## Reproducibility

Reviewed source links are pinned to commit `2247b35ea1c47c727d7a06e51fa280e12a863ff6`. Pattern semantics reference: [GitHub Actions workflow syntax — filter pattern cheat sheet](https://docs.github.com/en/actions/reference/workflows-and-actions/workflow-syntax#filter-pattern-cheat-sheet).
