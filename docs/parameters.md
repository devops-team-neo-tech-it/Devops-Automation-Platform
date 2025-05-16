## ⚙️ Pipeline Parameters

| Parameter     | Type     | Description                            | Default |
| ------------- | -------- | -------------------------------------- | ------- |
| `BRANCH_NAME` | `string` | Git branch to build                    | `main`  |
| `RUN_TESTS`   | `bool`   | Whether to run tests                   | `true`  |
| `ENV`         | `choice` | Deployment environment (dev, qa, prod) | `dev`   |
