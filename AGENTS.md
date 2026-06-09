# AGENTS — Workspace agent instructions

Purpose: Give AI coding agents immediate, minimal context and links to be productive in this repository.

Quick pointers
- **Architecture spec**: [docs/architecture-base.md](docs/architecture-base.md)
- **Agent role docs**: [agents/Arquiteto.md](agents/Arquiteto.md), [agents/pm.md](agents/pm.md), [.github/agents/Dev%20Backend.md](.github/agents/Dev%20Backend.md)

How to use
- Read the architecture spec first, then the role doc for the task you'll perform.
- Do not duplicate large docs; link to them from agent outputs.

Common commands
- Build: `./mvnw -DskipTests package`
- Run tests: `./mvnw test`

Environment notes
- Java 21, Spring Boot, PostgreSQL, Flyway (see `pom.xml` and `docs/architecture-base.md`).

Files of interest
- `docs/architecture-base.md` — system overview and spec
- `agents/` and `.github/agents/` — role-specific instructions for human and AI agents

Recommendations for agents
- Follow the role-specific doc in `agents/` or `.github/agents/` for process steps.
- Preserve existing documentation; prefer linking to files rather than copying content.
- When implementing code, run `./mvnw test` and report failures with stack traces and failing tests.
- Always use the linter after implementing the code.

Next suggested customizations
- Add a `.github/copilot-instructions.md` only if you need repository-level behavioral overrides.
- Create focused skills for testing, migration, or common refactors.
