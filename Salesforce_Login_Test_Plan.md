# Salesforce Login Page Test Plan

## 1. Document Control

| Field | Value |
|---|---|
| Document ID | TP-SFLOGIN-001 |
| Version | 1.0 |
| Status | Draft for stakeholder review |
| Prepared on | 2026-10-02 |
| Prepared by | QA Test Team (owner to be assigned) |
| Feature under test | Salesforce login entry page, `https://login.salesforce.com/?locale=in` |
| Planned test window / release | To be confirmed by the project manager |
| Business owner / approving authority | Product Owner and business owner, names to be confirmed |
| Classification | Internal project test artifact; do not store credentials in this document |

### Review and Approval

Approval means the reviewer accepts the scope, assumptions, test risks, and stated release criteria. Names and dates are intentionally pending; no approval is implied by this draft.

| Role | Reviewer / approver | Responsibility | Status / date |
|---|---|---|---|
| QA Lead / Test Manager | TBD | Review strategy, coverage, criteria, and readiness recommendation | Pending |
| Product Owner / Business Owner | TBD | Confirm business behavior and accept business risk | Pending |
| Business Analyst | TBD | Validate requirements and traceability | Pending |
| Development Lead | TBD | Confirm technical dependencies and defect process | Pending |
| Project Manager / Release Manager | TBD | Confirm schedule, release, and go/no-go governance | Pending |
| Security / Identity representative | TBD | Approve permitted authentication and security test boundaries | Pending |
| UAT Lead / delegate | TBD | Approve UAT scope and business acceptance | Pending |

## 2. Test Plan Overview

This plan defines how an enterprise QA team will assess the login experience reached through Salesforce's India-locale login URL. It covers the rendered login page and the user-visible authentication journey only to the extent that the project has an authorized Salesforce organization, test accounts, and an approved environment. Salesforce operates the shared login service; the project team must not infer that the public production endpoint is a project-owned test environment.

The plan is a baseline for project-specific review. Salesforce behavior, organization policy, identity-provider configuration, supported browser versions, release scope, service-level targets, and account policies must be confirmed with Salesforce documentation and the organization's owners before execution. Where these are unknown, this plan records a proposed test condition and an assumption rather than asserting a product requirement.

### Planning Sequence

1. Confirm approved requirements, Salesforce organization, target environment, account policies, supported clients, release changes, and test authorization. This prevents testing the wrong tenant or treating a production service as a test system.
2. Review the login workflow and map each approved requirement to scenario and test-case IDs. This establishes measurable coverage and defect traceability.
3. Prioritize scenarios by user impact, authentication risk, change impact, and likelihood. This puts successful access, credential rejection, privacy, and session controls first.
4. Prepare synthetic accounts and test data in an approved Salesforce sandbox or other explicitly authorized organization; verify access and environment dependencies without embedding secrets in test artifacts.
5. Execute smoke checks, then prioritized functional, negative, UI, accessibility, compatibility, and approved security checks; log and retest defects as described below.
6. Run impact-based regression and business UAT, publish metrics and residual risk, then obtain recorded release and UAT decisions.
7. Close testing with a reconciled traceability matrix, final reports, defect dispositions, and retained evidence under the organization's access and retention rules.

## 3. Test Objectives

- Verify that the login page loads reliably at the approved URL and that the India locale parameter is handled as specified by the product owner.
- Validate field behavior, submission, successful and unsuccessful authentication, user-facing messages, and approved recovery/navigation links.
- Confirm that session and Remember Me behavior match the approved organization policy and do not expose credentials or create unintended persistent access.
- Assess usability, responsive presentation, accessibility, and compatibility on the agreed browser, operating-system, and device matrix.
- Identify authentication, privacy, availability, and integration risks using authorized, non-destructive test methods.
- Provide objective evidence for release readiness and business UAT acceptance.

## 4. Scope

### 4.1 In Scope

- Page availability, initial rendering, locale parameter behavior, labels, controls, layout, and navigation on the login entry page.
- Username and password entry, field validation, submission by button and keyboard, loading/duplicate-submit behavior, and success/failure handling using authorized accounts.
- Remember Me selection and resulting username/cookie behavior, as clarified by approved requirements; password persistence is explicitly not presumed.
- Login error messages and recovery links, including navigation to password-recovery help where available; downstream recovery completion is included only if the project owns and authorizes that flow.
- Session creation, logout or return-to-login behavior, idle/expiry behavior, and browser back/cache behavior where these are within the agreed login feature boundary and can be tested safely.
- UI, usability, accessibility, responsive layout, cross-browser, compatibility, and risk-based regression checks.
- Approved security checks limited to normal user flows and authorized test accounts, including masking, transport/privacy observations, safe lockout/throttling verification, and session behavior.

### 4.2 Out of Scope

- Testing Salesforce platform internals, source code, infrastructure, availability SLAs, or any tenant not explicitly authorized by its owner.
- Penetration testing, credential stuffing, brute force, denial of service, bypass attempts, exploitation, or tests that could lock real users or degrade a shared production service.
- Creation, deletion, or modification of real customer data; use of real customer credentials; or capture of production secrets.
- Full Salesforce application functionality after authentication, except the minimum landing/session assertion needed to confirm login success.
- SSO, MFA, social login, identity-provider administration, password reset completion, account provisioning, and federation flows unless separately approved and added to scope. Visible links may receive navigation checks without validating the complete external flow.
- Formal certification or a claim of compliance with a security, accessibility, privacy, or regulatory standard. The plan proposes checks; the relevant owner must confirm the applicable standard and acceptance threshold.
- Load, stress, endurance, and broad service resilience testing of Salesforce's shared login service.

## 5. Application and Requirement Baseline

### 5.1 Feature Summary

The described page includes a username/email field, password field, Login/Submit control, Remember Me option, validation or authentication messages, and login success/failure behavior. Actual page controls and downstream behavior must be verified against the authorized environment; the list is a planning baseline, not a claim that every control is present in every configuration.

### 5.2 Provisional Requirement IDs

The IDs below are planning identifiers derived from the supplied feature description. The BA/Product Owner must replace or map them to approved requirements and acceptance criteria before test execution.

| ID | Provisional requirement area | Confirmation needed |
|---|---|---|
| REQ-LOGIN-01 | Login page loads and presents the approved locale/content | Expected locale, redirects, branding, and availability target |
| REQ-LOGIN-02 | Username and password fields accept/reject input as specified | Required-field rules, normalization, length and character limits |
| REQ-LOGIN-03 | Valid credentials authenticate the authorized user | Approved test tenant, account state, landing assertion |
| REQ-LOGIN-04 | Invalid or incomplete credentials are rejected safely | Exact error wording, response behavior, lockout policy |
| REQ-LOGIN-05 | Remember Me follows documented persistence behavior | Whether username, cookie, or other state is remembered; duration and clearing behavior |
| REQ-LOGIN-06 | Login controls support mouse and keyboard operation | Keyboard order, submit keys, disabled/loading behavior |
| REQ-LOGIN-07 | Recovery/navigation links route to approved destinations | Link targets and external-flow scope |
| REQ-LOGIN-08 | Session lifecycle follows organization policy | Expiration, logout, back-button, and reauthentication requirements |
| REQ-LOGIN-09 | Supported browsers/devices render and operate correctly | Official Salesforce and enterprise-supported matrix |
| REQ-LOGIN-10 | Login is usable and accessible to the agreed target | Applicable accessibility standard and conformance target |
| REQ-LOGIN-11 | Authentication and session controls meet approved security policy | Security owner, permitted test methods, account lockout/throttling rules |

## 6. Test Strategy

Testing is risk-based and evidence-driven. Prioritize authentication success/failure, safe handling of credentials, account/session state, and shared-service protection. Use the lowest environment that faithfully represents the approved configuration. Execute security-sensitive cases only after written authorization and with synthetic accounts. Do not interpret a successful page load as proof that the organization's full identity configuration is correct.

### 6.1 Testing Types and Purpose

| Type | Purpose and application to this feature |
|---|---|
| Functional | Verify page rendering, field behavior, submission, valid/invalid authentication, links, and session outcomes against approved requirements. |
| Positive | Prove an enabled synthetic account with correct credentials can authenticate and reach the expected authorized destination. |
| Negative | Verify incomplete, invalid, malformed, or disallowed inputs fail safely with usable feedback and no unintended session. |
| UI / Visual | Check labels, alignment, focus indication, error placement, responsive layout, text scaling, and absence of clipped/overlapping controls. Compare only with approved designs or baseline. |
| Validation | Check required fields, permitted input, boundary handling, normalization, and server-side rejection. Rules must come from the BA/Product Owner; do not invent username constraints. |
| Usability | Assess clarity of labels/errors, recovery discoverability, input correction, keyboard/mouse flow, and user comprehension with representative users where feasible. |
| Accessibility | Review semantics, accessible names, keyboard-only operation, focus order/visibility, error announcements, contrast and screen-reader interaction against the approved standard. |
| Compatibility / Cross-browser | Verify behavior across the agreed browser/OS/device matrix, versions, viewport sizes, and enterprise network configuration. |
| Security (authorized, non-invasive) | Observe masking, secret exposure, session behavior, safe rejection, lockout controls using bounded approved cases, and secure transport in normal flows. No exploitation or high-volume attempts. |
| Smoke | On a new build/environment, confirm URL reachability, page render, essential controls, and one approved safe login path before detailed testing. |
| Sanity | After a targeted fix/configuration change, quickly validate the changed behavior and directly related paths before broader regression. |
| Regression | Re-run affected login, error, session, locale, and browser paths after changes, fixes, Salesforce release impacts, or configuration updates. |
| UAT | Have designated business users validate representative business access and approved acceptance criteria in the agreed tenant; record acceptance and residual issues. |

### 6.2 Risk-Based Prioritization

- **P0 / release-critical:** valid login; invalid credential rejection; blank submission; session not created on failure; critical security/privacy behavior; approved primary browser smoke path.
- **P1 / high:** Remember Me behavior; recovery navigation; keyboard operation; supported-browser core flows; session termination/expiry; accessibility blockers on essential login controls.
- **P2 / normal:** lower-impact layout variations, secondary viewport combinations, non-blocking content details, and extended boundary combinations.
- Reassess priority at each release based on changed components, incidents, defect history, usage analytics where approved, and business impact. P0/P1 labels here are proposed project priorities and require QA Lead/Product Owner confirmation.

## 7. Test Approach and Execution Lifecycle

The test lifecycle is: **Requirement Analysis → Test Planning → Test Design → Test Data Preparation → Environment Setup → Test Execution → Defect Management → Retesting → Regression Testing → Test Reporting → Test Closure → Sign-off.**

| Phase | Main activities | Exit evidence / owner |
|---|---|---|
| Requirement analysis | Review acceptance criteria, Salesforce/org configuration, supported clients, security/privacy rules, and dependencies; record open questions and map provisional REQ IDs. | Approved baseline or explicitly documented unresolved assumptions; BA and QA Lead. |
| Test planning | Confirm scope, risk, schedule, roles, environment, entry/exit thresholds, and approvals. | Reviewed plan and test window; QA Lead / PM. |
| Test design | Derive scenarios and cases; identify equivalence classes, boundaries, negative paths, and regression impact. | Peer-reviewed cases linked to requirements; QA. |
| Test data preparation | Provision synthetic accounts and input sets; validate ownership, status, and reset path; store secrets in approved vault. | Data inventory with owner/expiry, never secret values in report; QA / org admin. |
| Environment setup | Confirm authorized tenant, URL, build/configuration, DNS/network/proxy, browsers, devices, time zone, and monitoring/contact path. | Environment checklist and smoke result; QA / platform owner. |
| Test execution | Run smoke first, then prioritized cases; capture actual result, environment, timestamp, and minimal redacted evidence. | Test-run records and daily status; QA. |
| Defect management | Log reproducible issues, severity/priority, evidence, assignment, impact, and linked requirement/case. | Defect IDs and triage decisions; QA Lead / triage group. |
| Retesting | Validate fix in target build/configuration; confirm the defect is resolved and no workaround was assumed. | Retest result linked to original defect; QA. |
| Regression testing | Select impacted and critical paths using change analysis; run targeted plus risk-based suite. | Regression results and residual risk; QA Lead. |
| Test reporting | Reconcile case statuses, defects, coverage, blockers, and risk; distribute agreed report at agreed cadence. | Published report and decisions/actions; QA Lead / PM. |
| Test closure | Resolve or obtain disposition for all findings; archive approved evidence; document lessons and escaped-defect actions. | Test summary and closure checklist; QA Lead. |
| Sign-off | Obtain business UAT acceptance and release decision from delegated owners; record exceptions explicitly. | Dated approval or documented conditional/no-go decision; PO / release authority. |

## 8. Test Scenario Matrix

Priority is provisional: P0 is release-critical, P1 high, P2 normal. Dependencies must be satisfied before execution. Expected results marked “per approved policy” require requirement confirmation before the case is baselined.

| Scenario ID | Requirement / feature | Test scenario / condition | Testing type | Priority | Expected outcome | Dependencies |
|---|---|---|---|---|---|---|
| SC-001 | REQ-LOGIN-01 | Open the exact India-locale URL in an approved browser, including initial and repeat navigation. | Smoke, functional, compatibility | P0 | Page loads over approved secure transport; locale/redirect/content matches the approved baseline; no broken essential controls. | URL availability, approved locale expectation, network access |
| SC-002 | REQ-LOGIN-01 | Load without the locale parameter and compare behavior with the India-locale URL. | Functional, compatibility | P2 | Fallback/default locale and redirect behavior match documented expectation; no unintended loss of query handling. | BA-confirmed default-locale behavior |
| SC-003 | REQ-LOGIN-02 | Inspect labels, username/password controls, Remember Me, Login control, and visible recovery links. | UI, usability, accessibility | P1 | Required controls are present, legible, correctly labeled, and match approved design/content. | Approved UI/content baseline |
| SC-004 | REQ-LOGIN-03 | Submit valid credentials for an enabled synthetic account. | Positive, functional, smoke | P0 | Authentication succeeds and reaches the expected tenant/application destination; no credentials appear in URL or page content. | Authorized org, enabled synthetic account, expected landing assertion |
| SC-005 | REQ-LOGIN-04 | Submit an unknown synthetic username with a non-secret invalid password. | Negative, validation, security | P0 | Access is denied; feedback follows approved policy and does not disclose whether the username exists. | Approved safe invalid account and error policy |
| SC-006 | REQ-LOGIN-04 | Submit a known synthetic username with an incorrect password. | Negative, validation, security | P0 | Access is denied; no authenticated session is created; feedback follows approved policy. | Synthetic account, failure-attempt limits/monitoring |
| SC-007 | REQ-LOGIN-04 | Submit an unknown username and incorrect password together. | Negative, security | P0 | Access is denied without account enumeration or sensitive diagnostic disclosure. | Approved test account policy |
| SC-008 | REQ-LOGIN-02 / 04 | Submit both fields blank. | Negative, validation | P0 | Submission is prevented or safely rejected; required-field guidance is clear; no session is created. | Required-field behavior confirmed |
| SC-009 | REQ-LOGIN-02 / 04 | Submit username blank and password populated, then username populated and password blank. | Negative, validation | P0 | Each incomplete combination is safely rejected with actionable, accessible feedback and no session. | Required-field behavior confirmed |
| SC-010 | REQ-LOGIN-02 | Enter leading/trailing spaces, mixed case, and allowed username formats. | Validation, functional | P1 | Normalization or rejection matches documented username rules; password is not silently altered. | Product rules for username normalization and case sensitivity |
| SC-011 | REQ-LOGIN-02 | Enter minimum/maximum and just-outside boundary lengths for each field using synthetic values. | Boundary, negative | P1 | Inputs are accepted/rejected per documented limits; page remains stable and provides safe feedback. | Documented input limits; avoid guessing limits |
| SC-012 | REQ-LOGIN-02 / 04 | Enter approved special characters, Unicode, and malformed strings in username; enter permitted character classes in password. | Negative, validation, security | P1 | Input is handled safely without script execution, layout breakage, truncation ambiguity, or sensitive echo; business rules are respected. | Approved character/length rules; non-exploitative payload set |
| SC-013 | REQ-LOGIN-05 | Select Remember Me, authenticate, close/reopen the browser, and revisit login as permitted. | Functional, privacy, regression | P1 | Only the specifically approved information/state is remembered for the documented duration; password remains unexposed and behavior matches policy. | Confirmed semantics, authorized account, browser policy |
| SC-014 | REQ-LOGIN-05 | Leave Remember Me unselected, authenticate, then revisit login. | Functional, privacy | P1 | No Remember Me persistence occurs beyond normal browser behavior; secret credentials are not retained or displayed. | Product definition of persistence; browser cache controls |
| SC-015 | REQ-LOGIN-05 | Clear relevant site data or use a fresh profile after Remember Me use. | Functional, privacy | P1 | Remembered state is removed according to documented clearing behavior; no residual password/session access remains unexpectedly. | Approved clearing procedure and test profile |
| SC-016 | REQ-LOGIN-02 | Type, paste, select, replace, and clear password; inspect display and accessible name. | UI, accessibility, security | P1 | Password is masked by default, editable as specified, never exposed in logs/URL, and control remains accessible. | Approved password-field behavior; browser developer tools restricted to authorized QA |
| SC-017 | REQ-LOGIN-06 | Submit by clicking Login and by keyboard (Tab/Shift+Tab, Enter, and supported key behavior). | Functional, accessibility, usability | P0 | Both supported paths submit the intended form once; focus order and visible focus are logical; no accidental activation. | Approved interaction design and supported browsers |
| SC-018 | REQ-LOGIN-06 | Double-click or rapidly activate Login once, using a valid and invalid synthetic submission. | Functional, resilience | P1 | Duplicate requests are prevented or safely handled; one user-visible outcome occurs; no unintended duplicate session or lockout. | Safe test account and approved request monitoring |
| SC-019 | REQ-LOGIN-04 | Trigger a normal authentication failure and inspect error text, placement, focus, and announcement. | Negative, UI, accessibility, usability | P0 | Message is understandable and policy-compliant, does not reveal secrets/account existence, is associated with the form, and is announced to assistive technology. | Approved error copy/policy; screen reader |
| SC-020 | REQ-LOGIN-07 | Activate password-recovery/help links and verify destination, locale handling, and return path where applicable. | Functional, navigation | P1 | Link reaches the approved destination without open redirect or broken navigation; downstream recovery is tested only if separately in scope. | Approved link targets and recovery test authorization |
| SC-021 | REQ-LOGIN-08 | After successful login, use the approved logout/return-to-login path, then attempt browser Back. | Session, security, regression | P0 | Protected content is not available from stale client state without valid session; reauthentication behavior matches policy. | Authorized org, logout procedure, expected session policy |
| SC-022 | REQ-LOGIN-08 | Leave an authenticated session idle for the documented timeout and attempt a protected action. | Session, security | P1 | Session expires or remains active according to documented policy; expired access requires appropriate reauthentication. | Documented timeout; dedicated synthetic account; approved test duration |
| SC-023 | REQ-LOGIN-08 | Interrupt network during page load or submission, then restore connectivity and retry safely. | Resilience, functional | P1 | Clear recoverable outcome appears; no false successful login, credential disclosure, or uncontrolled duplicate submission occurs. | Controlled test network; safe account |
| SC-024 | REQ-LOGIN-09 | Run core smoke and P0 cases on each agreed desktop browser/OS combination. | Cross-browser, compatibility, regression | P0 | Core flows, control rendering, errors, and navigation work consistently within the approved support matrix. | Confirmed matrix, current/previous browser versions |
| SC-025 | REQ-LOGIN-09 | Test agreed mobile browser/device combinations in portrait/landscape and representative viewport sizes. | Compatibility, responsive, usability | P1 | Fields and actions remain operable, readable, and unobscured; behavior meets the supported-device policy. | Confirmed device matrix and physical/device-lab access |
| SC-026 | REQ-LOGIN-10 | Complete login and validation flows using keyboard only and a representative screen reader. | Accessibility | P1 | Controls have meaningful names/roles/states; focus is visible and managed; errors and status changes are announced. | Agreed accessibility standard, assistive technology, test expertise |
| SC-027 | REQ-LOGIN-10 | Check zoom/text resizing, contrast, error color cues, target spacing, and responsive reflow. | Accessibility, UI | P1 | No essential content or action is lost; visual indicators do not rely on color alone; results meet the approved target standard. | Approved standard/design tokens |
| SC-028 | REQ-LOGIN-11 | Observe authentication over the approved network and verify secrets are not present in URL, visible errors, or approved client-side evidence. | Security, privacy | P0 | Credentials are transmitted only through approved secure channels and are not exposed in page output or test evidence. | Written authorization, approved proxy/tooling, security representative |
| SC-029 | REQ-LOGIN-11 | Verify account lockout/throttling using a small, pre-agreed number of attempts against a dedicated synthetic account. | Security, negative | P1 | Controls behave according to documented policy; test stops before any uncontrolled lockout or shared-service impact. | Written authorization, attempt ceiling, monitoring, unlock contact |
| SC-030 | REQ-LOGIN-11 | Inspect session behavior for secure cookie attributes and expected persistence using approved tools and policy. | Security, session | P1 | Session controls conform to Salesforce/org policy; test records observations without changing or attacking other users' sessions. | Security approval, authorized tenant, policy baseline |
| SC-031 | REQ-LOGIN-01 / 09 | Repeat P0 smoke after browser, Salesforce release, locale, or organization configuration change. | Smoke, sanity, regression | P0 | Critical page and authentication paths remain available and correct; any changed behavior is triaged before wider execution. | Change/release notice and authorized environment |
| SC-032 | REQ-LOGIN-03 / 04 | Repeat representative valid and invalid authentication cases after a defect fix. | Retest, regression | P0 | Original defect is resolved and directly related success/failure paths remain correct. | Fixed build/configuration, linked defect and test data |

## 9. Test Case Design, Review, and Maintenance Strategy

- Design cases from approved acceptance criteria and the provisional requirement map. Use positive/negative partitions, equivalence classes, boundary values, state transitions (logged out, failed attempt, authenticated, expired, logged out), and risk-based pairings of browser, viewport, and account state.
- Keep each case independently executable where practical. Record case ID, requirement ID, objective, priority, preconditions, environment, synthetic data reference, steps, expected result, actual result, status, evidence reference, run/build, tester, and linked defect.
- Do not place passwords, recovery codes, access tokens, or customer data in case text, screenshots, logs, source control, or reports. Refer to vault-managed data aliases and authorized retrieval procedures.
- Review cases with a peer before execution. QA Lead reviews P0/P1 coverage and traceability; BA/Product Owner confirms business outcomes; Security/Identity owner reviews security-sensitive cases; UAT owner reviews business scenarios.
- Baseline approved cases with version/history. When requirements, UI, Salesforce release behavior, tenant configuration, or support matrix changes, analyze impact, update linked cases, re-review affected expectations, and preserve prior execution history.
- Prioritize case execution by risk and prerequisites. Mark cases Not Run, Blocked, Pass, Fail, or Not Applicable with reason; do not count blocked or N/A as passed. A failed case requires a defect or documented approved disposition.
- Automation may be used where appropriate for repeatable regression, but this plan does not prescribe implementation technology. Human evaluation remains appropriate for usability and some accessibility checks.

## 10. Test Environment and Compatibility Coverage

### 10.1 Environment Requirements

| Area | Baseline / proposed coverage | Confirmation and control |
|---|---|---|
| Target | Prefer an organization-owned Salesforce sandbox or explicitly authorized non-production tenant. Use `login.salesforce.com/?locale=in` only for permitted page-level checks; do not submit test credentials to a shared production endpoint unless the service owner has authorized that exact activity. | Salesforce/org owner and Security must approve endpoint, tenant, test window, and account use. |
| URL / configuration | Record exact URL, locale parameter, tenant/org identifier (non-secret), configuration version, and any SSO/MFA policy affecting the path. | BA/org admin confirms expected routing and configuration. |
| Browsers | Proposed: current and previous supported versions of Chrome, Edge, Firefox, and Safari where applicable. | Confirm against Salesforce's current official browser requirements and enterprise support policy at execution time; this list is not a claim of Salesforce support. |
| Operating systems | Proposed: organization-supported Windows and macOS desktop versions; mobile OS only where mobile login is in scope. | Confirm supported versions and patch levels. |
| Devices / viewports | Desktop/laptop; agreed iOS/Android phones/tablets if supported; representative narrow, standard, and wide viewports, portrait and landscape. | Confirm usage analytics, accessibility needs, device lab, and support policy. |
| Assistive technology | At least one agreed screen reader/browser pairing plus keyboard-only checks; add platform-specific combinations based on user population. | Accessibility owner selects pairings and acceptance standard. |
| Network | Approved corporate network, VPN/proxy if representative, DNS, firewall allow-list, TLS inspection policy, stable connectivity, and a controlled interruption option for resilience tests. | Network team documents restrictions; never bypass controls without approval. |
| Test accounts | Dedicated synthetic accounts with known lifecycle, permissions, MFA/lockout rules, reset/unlock owner, and no customer data. | Org admin provisions and disables/removes accounts after the test window. |
| Tooling / evidence | Approved test management and defect tool; approved browser/device and accessibility tools; redacted evidence storage with access controls. | Project owner approves tools and retention. |
| Time and monitoring | Record time zone, test window, incident contact, service status source, and stop/escalation path. | PM/service owner publishes window and escalation route. |

### 10.2 Browser and Device Execution Model

- Run P0 cases on every agreed supported desktop browser/OS pairing; run full functional coverage on the primary enterprise browser and a representative second browser.
- Run the agreed mobile smoke and core form/accessibility paths on supported devices; do not imply mobile support until confirmed.
- Include current and previous supported browser versions when policy requires them; record exact version, OS build, viewport, zoom, locale, and network path in each execution.
- Expand the matrix after browser-specific defects or changes to page rendering, cookies, authentication redirects, or input handling. Avoid exhaustive Cartesian combinations where risk is low; document the risk-based selection.

## 11. Test Data Management

| Data class | Examples / use | Handling requirements |
|---|---|---|
| Valid account | Enabled synthetic user with minimum required permission in an approved test org. | Dedicated, owner-assigned, no real personal/customer data; credentials in an approved secret vault only. |
| Invalid account | Random non-existing synthetic identifier, with a non-secret invalid password. | Never use real email addresses or another user's identity; observe account enumeration policy. |
| Incorrect password | Known synthetic account paired with an intentionally wrong value. | Bound attempt count; coordinate lockout rules; reset/unlock after test. |
| Blank / partial | Both blank, username only, password only. | No account data needed; verify validation without repeated auth attempts. |
| Boundary / malformed | Values at and around confirmed length limits; whitespace, allowed/disallowed characters, Unicode as specified. | Do not assume limits or use destructive payloads; derive cases from approved requirements. |
| Account states | Enabled, disabled, locked, expired, or MFA/SSO-configured state only if specifically approved. | Org owner provisions and confirms expected response; do not cause real-user lockouts. |
| Remember Me / session | Dedicated account and isolated browser profile for persistence, clearing, logout, and expiry. | Separate profiles; delete test cookies/state at end; never store session tokens in evidence. |

Data lifecycle: request/provision → verify authorized state → execute within attempt ceiling → reset/unlock if needed → clear browser state → disable or remove accounts → confirm cleanup. Record data aliases and owner, not secret values. Follow organizational retention and privacy requirements for evidence.

## 12. Roles and Responsibilities

| Role | Responsibilities |
|---|---|
| QA Engineer / Tester | Analyze requirements, design/review cases, prepare approved data, execute and record results, report defects with reproducible evidence, retest fixes, and maintain traceability. |
| QA Lead / Test Manager | Own this plan and coverage; prioritize risks; confirm readiness; coordinate execution and triage; monitor metrics; recommend go/no-go; approve test closure. |
| Developer / Development Lead | Clarify technical behavior, analyze and fix defects, provide build/configuration details, perform developer verification, and identify change impact. |
| Business Analyst | Baseline business requirements, field/error/link behavior, acceptance criteria, and traceability; resolve requirement ambiguity. |
| Product Owner / Business Owner | Prioritize scope, confirm business risk and expected outcomes, accept or reject residual risk, and provide release/UAT approval authority as delegated. |
| Project Manager | Coordinate schedule, resources, release dependencies, status cadence, risks, and escalation; ensure decision owners are available. |
| Salesforce Org Administrator / Identity Owner | Provision and clean up synthetic accounts; confirm org/login/MFA/lockout policies; approve tenant and account use; support environment incidents. |
| Security / Privacy representative | Define allowed security checks, data handling, evidence controls, escalation, and response to suspected exposure or unauthorized behavior. |
| Platform / Network team | Confirm connectivity, DNS, proxy/VPN behavior, monitoring, service-status source, and test-window support. |
| UAT Lead / Business Users | Prepare representative business journeys, execute UAT, raise findings, confirm business acceptance, and sign UAT outcome. |
| Release Manager / Change Authority | Confirm release scope, deployment/change window, go/no-go governance, and approved exceptions. |

## 13. Entry Criteria

Testing may begin when all applicable conditions are met:

1. Scope, provisional requirements, acceptance criteria, locale expectations, and supported browser/device matrix are reviewed; unresolved items are recorded with owner and due date.
2. QA Lead and relevant Product Owner/BA approve the plan for execution; security-sensitive cases have written authorization and boundaries.
3. An authorized target organization and URL are available, identified, and confirmed as safe for the planned activity; target is not assumed to be a sandbox merely because it is accessible.
4. Required test accounts are provisioned, verified, dedicated to testing, and have documented reset/unlock/cleanup owners; no production/customer credentials are used.
5. Environment checklist is complete: DNS/network, browser/device versions, locale, tenant configuration, dependencies, monitoring, incident contacts, and test window confirmed.
6. Test cases for the planned cycle are reviewed and traceable to approved or explicitly provisional requirements; P0/P1 cases have expected results and prerequisites.
7. Build/configuration change is deployed and identified; developer verification is complete for the changed login scope; no known environment blocker prevents meaningful execution.
8. Defect-management project, severity/priority definitions, evidence location, reporting cadence, and escalation/stop criteria are available to the team.
9. For a new environment/release, smoke prerequisites and approved service-status reference are known. If an entry condition is waived, record the risk and approving owner before proceeding.

## 14. Exit Criteria and Release Recommendation

A test cycle is complete only when all criteria below are evaluated and results recorded. Any exception requires a named decision owner, rationale, expiry/review date, mitigation, and explicit release disposition.

- 100% of in-scope approved requirements are mapped to at least one scenario/case, or have a documented exclusion/accepted gap.
- 100% of P0 and P1 planned cases have a final status (Pass, Fail with linked defect, Blocked with approved disposition, or Not Applicable with rationale); no P0 case remains Not Run.
- At least 95% of all in-scope planned cases are executed, unless QA Lead and Product Owner approve a lower target with quantified residual risk. Passed cases are reported separately; Blocked/N/A are not counted as executed passes.
- All P0 cases pass. No open Severity 1 or Severity 2 defect remains without a formally approved exception and mitigation; the default recommendation for an unresolved Sev 1 or Sev 2 is no-go.
- All fixed P0/P1 defects are retested; impact-based regression is complete for the release change; failed retests are reopened or linked to a new defect.
- UAT acceptance is recorded from the delegated business approver, or a documented no-go/conditional decision is recorded. QA sign-off does not substitute for business acceptance.
- Security/accessibility findings in agreed scope are dispositioned by their accountable owners; no unauthorized test activity or unresolved suspected credential exposure remains.
- Final metrics, traceability, defects, known limitations, environment/build details, residual risks, and evidence references are reconciled and included in the test summary.
- QA Lead publishes a recommendation; release authority records the final Go / Conditional Go / No-Go decision.

These thresholds are proposed governance baselines, not an existing Salesforce or customer SLA. Confirm them during plan approval.

## 15. Defect Management

### 15.1 Workflow

1. **Identify:** Tester records actual result, expected result, case/requirement ID, build/configuration, environment, timestamp/time zone, reproduction steps, frequency, and minimal redacted evidence.
2. **Log:** Create one defect per independently actionable issue; include business impact, affected browsers/accounts (aliases only), first occurrence, workaround, and suspected scope without asserting root cause prematurely.
3. **Triage:** QA Lead, Product Owner/BA, Development Lead, and Security/Identity owner as needed confirm reproducibility, severity, priority, ownership, target release, and data/environment impact.
4. **Assign and investigate:** Development or responsible platform owner analyzes cause and impact; QA records linked requirements/cases and monitors target fix/release.
5. **Fix and verify:** Developer records fix/build and developer verification. QA retests the original reproduction path in the target environment.
6. **Regression:** QA runs risk-based related cases (authentication, validation, errors, locale, session, browser/device, Remember Me) based on code/configuration impact.
7. **Close or reopen:** Close only when acceptance criteria pass and evidence is linked. Reopen if reproducible; create a separate linked defect if the behavior is distinct.
8. **Report:** Include new/open/closed/reopened defects, aging, severity/priority, escape/UAT/production status, blockers, and decisions in agreed reports.

### 15.2 Severity and Priority

Severity describes impact; priority describes urgency/order of work. QA proposes both; triage and business/release owners confirm. The examples below are guidance, not fixed organizational SLAs.

| Severity | Definition | Login-related example |
|---|---|---|
| Sev 1 - Critical | Broad outage, material security/privacy exposure, or complete loss of a critical login capability with no viable workaround. | Authorized users broadly cannot reach login/authenticate; credentials or active sessions are exposed; authentication failure grants unauthorized access. |
| Sev 2 - High | Major user group or critical path is materially impaired; limited or unacceptable workaround. | Valid users consistently cannot authenticate in a supported primary browser; session control defect creates substantial access risk; primary validation prevents legitimate login. |
| Sev 3 - Medium | Partial feature degradation or limited user impact with a reasonable workaround. | Recovery link fails while direct approved recovery remains available; a supported secondary browser has a layout issue that does not block login. |
| Sev 4 - Low | Cosmetic, low-impact, or documentation/content issue with no material functional/security impact. | Minor spacing or non-critical text mismatch with no obstruction or accessibility blocker. |

| Priority | Definition | Example |
|---|---|---|
| P0 - Immediate | Must be addressed before further release progression; requires immediate triage. | Current suspected credential exposure or widespread login outage. |
| P1 - High | Fix in current release/test cycle unless an accountable owner approves otherwise. | Valid login broken for a supported, commonly used browser. |
| P2 - Normal | Schedule based on release capacity and impact; workaround may exist. | Non-blocking locale-specific visual issue. |
| P3 - Low | Backlog or planned improvement; no material release risk. | Minor cosmetic refinement. |

Do not assign a severity solely from a priority label. Record affected user count/scope when known, reproducibility, workaround, data/security impact, and release timing.

### 15.3 Special Defect Handling

- **Blocker:** Stop affected testing/release activity; notify QA Lead, PM, Development Lead, and service/security owner as relevant. Preserve redacted evidence, state affected scope, and resume only after authorized clearance and a successful targeted smoke.
- **Critical/security:** Stop the triggering activity, do not continue probing, do not distribute secrets/evidence broadly, and notify the designated incident/security channel immediately. Follow the organization's incident response process; no public disclosure or unapproved reproduction.
- **UAT finding:** UAT Lead logs the issue with business scenario and impact; QA triages and links it to requirements. Business owner decides acceptance, workaround, deferral, or rejection; UAT sign-off documents open items explicitly.
- **Production/escaped defect:** Follow incident/change process first; assess customer/business impact, security/privacy implications, and safe rollback/workaround. Preserve only authorized redacted evidence, reproduce in a non-production tenant where possible, add a regression case, perform root-cause/escape analysis, and track corrective actions. Never reproduce against production in a way that risks users or service stability.

## 16. Defect Triage and Service Targets

Triage cadence, response times, and fix SLAs are **TBD** and must be agreed by QA Lead, PM, Development Lead, Product Owner, and Security owner before execution. No response-time SLA is assumed by this plan. At minimum, triage P0/P1 defects on the same working day or sooner where project policy requires; establish named on-call/escalation contacts for a release window. Record all accepted deferrals with owner, rationale, compensating control, and review/release date.

## 17. Regression Testing Strategy

Run regression after login-related code/configuration changes, defect fixes, Salesforce release changes that affect the entry/login path, browser/OS changes, identity policy changes, or any incident/escaped defect. Determine impact through change notes, developer analysis, requirement links, dependencies (locale, cookies, redirects, MFA/SSO settings), and prior defect history.

- Always execute smoke and the P0 happy-path/rejection cases for an affected release.
- For field/validation changes, include blank, malformed, boundary, and error-message/accessibility cases.
- For Remember Me/cookie/session changes, include selected/unselected persistence, clearing, logout, expiry, and back-button cases.
- For page/locale/navigation changes, include target URL, redirect, locale, recovery links, UI, and responsive checks.
- For browser or Salesforce release changes, run P0 across the supported matrix and prioritize historically unstable combinations.
- Run broader P1/P2 regression according to risk and time; document selection, omitted cases, and residual risk. Add an escaped-defect regression case once expected behavior is approved.

## 18. Smoke and Sanity Strategy

**Smoke** is a short build/environment gate: confirm approved URL reachability, correct locale/page render, presence and operability of primary controls, one valid synthetic login when authorized, one safe invalid attempt, and logout/session outcome. Run after deployment/configuration change and before detailed execution. Stop and raise an environment/blocker issue if a critical smoke check fails.

**Sanity** is a focused check after a narrowly scoped fix or configuration update: verify the changed behavior, original defect reproduction, and closest dependent path. A passing sanity check does not replace required regression. Keep test data and attempt counts within account policy.

## 19. Cross-Browser, Device, and Compatibility Strategy

- The Product Owner/enterprise support owner must confirm official Salesforce browser requirements and the organization's supported matrix at plan approval. Proposed browsers in Section 10 are candidates, not a product-support assertion.
- Cover supported desktop combinations, agreed mobile browser/device combinations, locale, viewport sizes, zoom/text scaling, and enterprise network path. Record exact versions and device characteristics.
- Compare behavior, not only appearance: input entry, autocomplete/password manager policy, keyboard submit, cookie/redirect handling, errors, recovery navigation, and session state.
- If a browser is unsupported, record it as excluded with owner approval; do not present lack of testing as a pass.
- Re-test combinations implicated by defects and release notes; prioritize actual enterprise usage and risk over exhaustive combinations.

## 20. Accessibility and Usability Strategy

Use the accessibility standard and target level approved by the organization; WCAG 2.2 AA may be adopted as a proposed baseline only after accessibility-owner confirmation. Check:

- Programmatic labels/names, roles, states, required indicators, and logical reading order for username, password, Remember Me, Login, and recovery controls.
- Full keyboard operation, logical Tab/Shift+Tab order, visible focus, no keyboard trap, Enter behavior, and focus placement after errors or page updates.
- Error association with relevant fields, clear correction guidance, announcement to screen readers, and no color-only communication.
- Contrast, text resize/zoom, responsive reflow, touch target usability, page language/locale, and absence of clipped or obscured content.
- Password manager and browser autofill interactions where supported and permitted; ensure user can review/correct entered username and password without exposing secrets.
- Representative screen-reader/browser pairings chosen by the accessibility owner; record assistive technology and version. Automated scans supplement, but do not replace, manual keyboard/screen-reader evaluation.

Log accessibility defects with affected control, assistive technology/browser, steps, impact, standard criterion if confirmed, and redacted evidence.

## 21. Security and Privacy Test Considerations

All security activities require written scope, named authorization, approved tenant/accounts, test window, attempt ceilings, monitoring, and stop/escalation contacts. Use non-invasive observations and bounded normal flows. Do not perform brute force, credential stuffing, denial of service, exploit payloads, session theft, bypasses, or testing against accounts/services not owned or authorized by the organization.

- Verify password masking by default and confirm credentials are absent from URL, visible errors, screenshots, test logs, and reports. Inspect only with approved tools and authorization.
- Verify valid credentials are required for protected access; invalid attempts do not create authenticated state; errors do not unnecessarily disclose whether an account exists or reveal internal details.
- Confirm HTTPS/secure transport and, where approved, observe session cookie attributes and redirect behavior against Salesforce/org policy. Do not alter or replay other users' tokens.
- Validate logout, expiry, back-button/cache, Remember Me, and shared-device behavior against documented policy. Do not assume Remember Me stores only a username until verified.
- Assess lockout/throttling only with a pre-approved small attempt ceiling on a dedicated synthetic account, with monitoring and a reset contact. Stop before reaching an unapproved lockout threshold.
- Review sensitive-data handling, consent/privacy notices if in scope, browser persistence, evidence retention, and account cleanup with Security/Privacy owner.
- Immediately stop and escalate any suspected credential exposure, unexpected access, shared-service degradation, or real-user impact under the incident process.

This plan is not a penetration-test authorization, security certification, or vulnerability assessment of Salesforce infrastructure.

## 22. UAT Strategy

### Preparation

- Product Owner/UAT Lead confirms business objectives, approved acceptance criteria, intended user roles, supported tenant, UAT window, business-user roster, training/communications, accessibility needs, and decision authority.
- Provide stable authorized tenant, synthetic UAT accounts, test data aliases, expected outcomes, defect logging instructions, and support/escalation contacts.
- Select representative business scenarios: navigate to the India-locale login, authenticate as an approved user, recover from invalid credentials, verify expected landing access, and end/re-establish session according to policy. Include Remember Me only where business policy defines it.

### Execution and Defect Handling

- Business users execute and record actual outcome, case ID, role/account alias, browser/device, and issue; do not record passwords or customer data.
- QA supports reproducibility and impact assessment; Development/Org owner investigates. Security/privacy concerns use the incident path. UAT findings follow the defect workflow with business impact and a linked scenario.
- UAT Lead tracks Pass/Fail/Blocked/Not Run and retest results. No finding is silently waived; accepted workarounds and deferred defects require named business approval.

### Acceptance and Sign-off

UAT exit requires all agreed critical business scenarios to pass, no unresolved blocker/critical business issue without a formally accepted disposition, test results and open risks reviewed, and explicit sign-off from the delegated Product Owner/business authority. Record approver, date, build/configuration, accepted exceptions, and decision (Accepted, Conditional, Rejected). QA recommends readiness but does not sign on behalf of the business.

## 23. Requirements Traceability

Maintain a controlled RTM with this chain: **Business Requirement → Scenario ID → Test Case ID → Test Run/Result → Defect ID → Fix/Retest Result → Release/UAT Decision.** Each record includes owner, version, status, and links in the approved test/defect management system.

| RTM field | Minimum content |
|---|---|
| Requirement | Approved ID, description, source/version, owner, acceptance criteria |
| Scenario / case | Scenario ID, case ID, test type, priority, positive/negative classification |
| Execution | Run ID, build/configuration, environment/browser/device, date, tester, status, evidence reference |
| Defect | Defect ID, severity/priority, status, affected requirement/cases, target fix, disposition |
| Closure | Retest/regression outcome, residual risk, UAT/release approval reference |

Review coverage before execution and at closure. Report both uncovered requirements and requirements with failing/blocked cases. For provisional REQ IDs in this plan, replace or map them to the approved business requirement baseline before formal sign-off.

## 24. Test Metrics and Reporting

Use a single agreed source of truth and state reporting cut-off/time zone. Report counts with definitions; do not combine blocked/N/A with passed or hide reopened defects.

| Metric | Definition / calculation |
|---|---|
| Total planned cases | Count of in-scope baselined cases for the release, with version noted. |
| Executed cases | Pass + Fail results; report blocked and N/A separately. If rerun, use latest result for status and retain attempt history. |
| Passed / failed / blocked / not executed / N/A | Count by latest execution status; show totals reconcile to planned cases. |
| Execution progress | (Pass + Fail + Blocked + approved N/A) / total planned × 100; also report executed-only progress (Pass + Fail) / total planned × 100 to avoid masking blockers. |
| Pass rate | Pass / (Pass + Fail) × 100; exclude blocked, not-run, and N/A; state denominator. |
| Requirement coverage | Requirements linked to at least one designed case / in-scope approved requirements × 100. Also report execution coverage separately. |
| Defect count | Open/new/reopened/closed counts by severity, priority, age, release, and source (QA/UAT/production/escaped). |
| Defect closure percentage | Closed defects / (closed + open actionable defects) × 100; report waived/deferred separately and do not count them as closed fixes. |
| Defect aging | Open duration by severity/priority against project-agreed triage/fix targets. |
| Retest/regression | Fixes retested, retest pass/fail, regressions run/pass/fail, and outstanding impact areas. |
| Risk status | High/medium/low risks, mitigations, owners, due dates, and accepted residual risks. |

### Reporting Cadence and Content

| Report | Cadence / audience | Minimum content |
|---|---|---|
| Daily execution update | Daily during active test; QA, Dev, BA, PM | Planned vs actual, status counts, P0/P1 outcomes, new/open defects, blockers, environment/data issues, next-day plan, decisions needed. |
| Weekly summary | Weekly; project stakeholders | Trend of execution/coverage/defects, risk changes, aging, regression status, schedule forecast, dependencies, mitigation owners. |
| Milestone/release report | At test-cycle or release gate; release authority and stakeholders | Entry/exit criteria, scope, build/environment, results, critical defects, exceptions, residual risk, QA recommendation. |
| UAT report | At agreed UAT checkpoints and closure; PO/UAT/PM | Business cases/results, participant coverage, findings, retests, accepted workarounds, UAT acceptance status. |
| Final test summary | At closure; all approvers | Scope and exclusions, baseline/version, metrics, traceability, defect disposition, environment/browser matrix, risks, lessons, recommendation, sign-off references. |

## 25. Risks and Mitigation

| Risk | Likelihood / impact | Mitigation and contingency | Owner |
|---|---|---|---|
| Public/shared login endpoint is mistaken for a test environment; test activity affects real users or service. | Medium / Critical | Prefer authorized sandbox; obtain written scope and attempt limits; prohibit load/brute-force activity; stop on unexpected impact. | QA Lead / Salesforce Org Owner / Security |
| Valid credentials or account access unavailable, expired, or misconfigured. | Medium / High | Provision dedicated synthetic accounts early; validate permissions and reset owner; use entry criteria to block meaningful auth testing until resolved. | Org Admin |
| Invalid credential behavior leaks account existence or sensitive diagnostics. | Medium / High | Include safe invalid-user/password comparisons; review approved messages; escalate suspected disclosure to Security. | Security / Identity Owner |
| Excessive failed attempts lock test or real accounts. | Medium / High | Use test account only, bounded attempt ceiling, monitor and reset contact; no unapproved brute force. | QA Lead / Org Admin |
| Session or Remember Me behavior differs by browser, policy, or shared device. | Medium / High | Define expected semantics; test isolated profiles, logout/expiry/clearing across matrix; document exception and privacy impact. | Identity Owner / QA |
| Browser, Salesforce release, or locale change causes compatibility regression. | Medium / High | Confirm support matrix; monitor release notes/change notices; run smoke and impact-based regression after changes. | Release Manager / QA |
| External Salesforce service, DNS, network, proxy, MFA, SSO, or identity-provider dependency is unavailable. | Medium / High | Track dependencies and status source; coordinate window; distinguish environment block from product defect; reschedule or report blocked coverage. | Platform / Identity Owner |
| Requirements, error policy, browser support, or acceptance criteria are incomplete. | High / Medium | Maintain assumptions/questions with owner and due date; do not baseline guessed behavior; escalate before affected execution. | BA / Product Owner |
| Test evidence exposes credentials, tokens, personal data, or account identifiers. | Low / Critical | Use synthetic data, redact evidence, restrict storage access, train testers, and follow incident response if exposure is suspected. | QA Lead / Security / Privacy |
| Accessibility issue prevents users from authenticating. | Medium / High | Include keyboard and screen-reader cases early; agree target standard; prioritize essential control barriers and validate fixes with assistive technology. | Accessibility Owner / QA |
| UAT users or approver unavailable within release window. | Medium / High | Nominate delegates and schedule early; track UAT readiness as a dependency; no implied business approval if sign-off is absent. | Product Owner / PM |
| Test environment differs materially from production configuration. | Medium / High | Record configuration differences; request parity review; assess whether results generalize; state limitations in release recommendation. | Org Admin / Release Manager |

Likelihood and impact are initial qualitative assessments and must be reviewed at project kickoff; they are not measured incident probabilities.

## 26. Assumptions

1. The supplied URL and feature list define the initial target only; exact content, redirects, authentication policies, error messages, and current Salesforce behavior must be confirmed at execution time.
2. An organization owner can provide a dedicated authorized Salesforce tenant and synthetic accounts if authentication testing is required. Without that authorization, scope is limited to page-level checks that do not submit credentials.
3. Proposed browsers, OSs, viewport sizes, accessibility target, priority labels, and exit thresholds require stakeholder approval; they are not represented as contractual or official Salesforce support commitments.
4. Salesforce login behavior may be affected by tenant configuration, SSO, MFA, identity provider, cookies, browser policy, and Salesforce service changes; the applicable configuration will be recorded per run.
5. An approved issue tracker, test-results source, evidence repository, vault, release calendar, and incident/escalation process will be identified by the project. Their names are TBD.
6. Test data is synthetic and test accounts can be reset/unlocked and removed after execution.
7. Time zone, test window, support coverage, and operational response targets will be agreed before execution; no service or defect SLA is assumed here.

## 27. Dependencies

- Salesforce service availability and current behavior/documentation for the target login route.
- Approved Salesforce organization, org administrator, tenant configuration, synthetic test users, account-state management, and permitted authentication routes.
- Product/BA confirmation of locale, fields, validation, errors, Remember Me, recovery, session, and success destination requirements.
- Security/Privacy approval, safe attempt ceilings, secure test-data vault, authorized tooling, evidence handling, and incident contacts.
- Supported browser/OS/device matrix, accessibility standard, device/assistive technology access, and representative network/proxy setup.
- Development/release details, Salesforce release notices, change/configuration identifiers, defect management, test management, and UAT business-user availability.

## 28. Constraints

- The public Salesforce login service is shared and outside direct project control; production access and security testing are constrained by explicit authorization and service-owner policy.
- Salesforce UI, redirects, browser support, and authentication flows may change independently of a project release.
- Login outcomes can depend on MFA/SSO, identity-provider availability, account policy, network controls, browser privacy settings, cookies, and tenant configuration.
- Real-user credentials, real customer data, and uncontrolled authentication attempts are prohibited.
- Time, device lab, accessibility expertise, browser versions, account quotas, and UAT availability may limit exhaustive combinations; uncovered combinations must be reported.

## 29. Test Deliverables

| Deliverable | Owner | Minimum content / acceptance |
|---|---|---|
| Test Plan | QA Lead | Approved scope, strategy, assumptions, criteria, environment, risks, roles, and sign-off. |
| Test Scenarios | QA | Scenario matrix with IDs, types, priorities, outcomes, dependencies, and requirement links. |
| Test Cases | QA | Reviewed steps, preconditions, test-data aliases, expected results, execution fields, and traceability. |
| Test Data Register | QA / Org Admin | Synthetic account aliases, data purpose, owner, environment, validity, reset/cleanup date; no secrets. |
| Requirement Traceability Matrix | QA / BA | Requirement → scenario → case → result → defect → retest → UAT/release disposition. |
| Environment / Compatibility Record | QA / Platform Owner | URL/tenant identifier, config/build, browser/OS/device, network, locale, and known differences. |
| Defect Reports | QA / triage owners | Reproducible issue, severity/priority, links, evidence, owner, fix, retest, disposition. |
| Daily / Weekly Execution Reports | QA Lead | Reconciled metrics, blockers, defects, risks, trend, forecast, decisions/actions. |
| Test Summary Report | QA Lead | Final scope/results/coverage/defects/risks/recommendation and residual limitations. |
| UAT Sign-off | UAT Lead / Product Owner | Business scenario outcomes, open items, accepted exceptions, approval decision/date. |
| Test Closure Report | QA Lead | Closure checklist, traceability reconciliation, artifacts, defect disposition, lessons, approvals. |

## 30. Test Plan Review, Approval, and Change Management

### Review and Approval Workflow

1. QA author checks completeness, measurable criteria, traceability, data safety, and internal consistency.
2. QA Lead reviews risk coverage, scenario priorities, environment feasibility, and resource/schedule assumptions.
3. BA/Product Owner review business behavior, scope, success/error expectations, UAT acceptance, and residual-risk authority.
4. Development, Salesforce Org/Identity, Security/Privacy, Accessibility, Platform/Network, and Release representatives review their relevant dependencies and safety boundaries.
5. Resolve comments or record accepted comments with owner and rationale. Update version history and obtain explicit approval from designated approvers before execution.
6. Store the approved version in the controlled project repository. A verbal approval is recorded by the document owner with approver and date according to project policy.

### Change Control

Any change to requirements, locale, scope, tenant/environment, account/authentication policy, browser matrix, Salesforce release, schedule, risk, exit threshold, or UAT/release criteria triggers impact analysis. The requester records change, reason, affected sections/requirements/cases, risks, schedule/resource impact, and requested effective date. QA Lead and impacted owners review; Product Owner/release authority approve scope/acceptance changes. Update version, change log, RTM, cases, data, environment, and reports; communicate the approved baseline before execution. Emergency production or security changes follow incident/change governance first and are incorporated into this plan afterward with traceable approval.

| Version | Date | Change | Author / approval |
|---|---|---|---|
| 1.0 | 2026-10-02 | Initial baseline for stakeholder review; project-specific requirements and approvals pending. | QA Test Team / Pending |

## 31. Test Reporting and Escalation

The QA Lead is responsible for accurate status and escalation. Report actual evidence and impact, not subjective “green” status. Escalate immediately through the agreed channel for suspected credential exposure, unauthorized access, broad login outage, real-user impact, or a blocker preventing safe execution. Escalate other P0/P1 defects and exit-criteria risks to QA Lead, Development Lead, Product Owner, and PM within the project-agreed triage window. Record decision owner, date/time, action, and due date. Reports include an explicit statement when testing is blocked, incomplete, outside authorization, or based on unconfirmed assumptions.

## 32. Test Closure and Final Sign-off

QA Lead closes the cycle after exit criteria are assessed, case and requirement counts reconcile, defects are fixed or dispositioned, regression/retest outcomes are linked, test data is cleaned up, evidence is stored under policy, and residual risks are documented. The Test Summary Report states tested build/configuration, environment and browser matrix, scope/exclusions, metrics, defect status and aging, UAT outcome, known limitations, accepted exceptions, and QA recommendation.

| Decision | Meaning | Required authority / record |
|---|---|---|
| Go | Exit criteria met; no unacceptable residual risk identified. | Release authority records decision; QA Lead and Product Owner recommendations retained. |
| Conditional Go | Explicitly accepted open risk or deferred item with owner, mitigation, due date, and rollback/monitoring plan. | Product Owner/business risk owner and release authority approve in writing; QA documents dissent/limitations if applicable. |
| No-Go | Critical criteria not met, unacceptable defect/risk, authorization/environment invalid, or business UAT rejected. | Release authority records decision and exit conditions for re-entry. |
| UAT Accepted / Conditional / Rejected | Business acceptance outcome for the agreed UAT scope. | Delegated Product Owner/business approver signs with date, scope, build/configuration, and exceptions. |

QA sign-off confirms testing was performed and reported against the approved plan; it is not a warranty that the service is defect-free and does not replace business, security, or release approval.

## 33. Approval Record

| Approval role | Name | Decision | Date | Comments / accepted exceptions |
|---|---|---|---|---|
| QA Lead / Test Manager | TBD | Pending | TBD | |
| Product Owner / Business Owner | TBD | Pending | TBD | |
| Business Analyst | TBD | Pending | TBD | |
| Development Lead | TBD | Pending | TBD | |
| Security / Identity Owner | TBD | Pending | TBD | |
| UAT Lead / Delegate | TBD | Pending | TBD | |
| Release Authority | TBD | Pending | TBD | |
