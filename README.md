<h1 align="center">
    <img src="src/main/resources/META-INF/pluginIcon.svg" width="84" height="84" alt="logo"/>
    <br/>
    Committle
</h1>

<p align="center">
    <a href="https://plugins.jetbrains.com/plugin/23100-commit-message-template"><img alt="plugin's version" src="https://img.shields.io/jetbrains/plugin/v/23100-commit-message-template?style=flat-square&logo=jetbrains"/></a>
    <a href="https://plugins.jetbrains.com/plugin/23100-commit-message-template"><img alt="plugin's downloads" src="https://img.shields.io/jetbrains/plugin/d/23100-commit-message-template?style=flat-square"/></a>
    <a href="https://plugins.jetbrains.com/plugin/23100-commit-message-template"><img alt="plugin's rating" src="https://img.shields.io/jetbrains/plugin/r/stars/23100-commit-message-template?style=flat-square"/></a>
    <a href="https://github.com/EugeneKozhun/commit-message-template/actions/workflows/publish-workflow.yml"><img alt="deploy" src="https://img.shields.io/github/actions/workflow/status/EugeneKozhun/commit-message-template/publish-workflow.yml?label=deploy&style=flat-square&logo=github"/></a>
    <a href="https://github.com/EugeneKozhun/commit-message-template/blob/main/LICENSE.md"><img alt="plugin's license" src="https://img.shields.io/github/license/EugeneKozhun/commit-message-template?style=flat-square"/></a>
</p>

✨ **Committle** (ex. Commit Message Template)
is a JetBrains IDE plugin that simplifies writing consistent and meaningful commit messages.
Forget messy commits—stick to your project’s conventions with ease!

_Compatible with [Conventional Commits](https://www.conventionalcommits.org/)_

## 🚀 Key Features

- Default commit message template: Save time by using a predefined structure.
- Customizable variables: Dynamically set values like:
    - Task id (Jira, Asana, etc.)
    - Scope (e.g., feature area)
    - Type (feat, fix, etc.)
    - Default values for variables
- Whitespace and caret formatting: Fine-tune alignment and position.
- Shortcut for applying the template: Fast and efficient workflow.

## 🛠️ Installation

1. Open JetBrains IDE.
2. Navigate to `File > Settings > Plugins`.
3. Select the `Marketplace` tab.
4. Search for `Committle` and click `Install`.

## 💡 How to Use

Using the plugin is straightforward and beginner-friendly:

1. When committing changes, press the button or use the shortcut to apply the template.
2. Edit the generated message with relevant details.
3. Commit your changes effortlessly.

## ⚙️ Customization

Customize the plugin to your needs:

1. Go to `File > Settings > Version Control > Committle`.
2. Enter your desired commit message template.
3. Set whitespace rules.
4. (Optional) Configure your own rules for the variables.

### Template Example:

```
$TYPE ($SCOPE): Commit message 

Description of changes

Closes $TASK-ID
```

## 🏆 Benefits

- **Consistency**: Your commit messages will always follow the agreed-upon structure.
- **Time-saving**: No need to manually format messages every time.
- **Simplicity**: Works out of the box with minimal configuration.

## 🤝 Support

Found a bug? Have an idea for improvement? Feel free to create an issue in the
repository: [GitHub Issues](https://github.com/EugeneKozhun/commit-message-template/issues).

--- 
© 2025 Eugene Kozhun
