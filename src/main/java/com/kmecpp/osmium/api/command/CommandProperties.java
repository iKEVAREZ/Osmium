package com.kmecpp.osmium.api.command;

public class CommandProperties {

	private String[] aliases;
	private String description = "";
	private String permission = "";
	private String usage = "";
	private boolean admin;
	private boolean playersOnly;

	public CommandProperties(String... aliases) {
		this.aliases = aliases;
	}

	public CommandProperties(Command command) {
		this.aliases = command.aliases();
		this.description = command.description();
		this.permission = command.permission();
		this.usage = command.usage();
		this.admin = command.admin();
		this.playersOnly = command.playersOnly();
	}

	public CommandProperties setDescription(String description) {
		this.description = description;
		return this;
	}

	public CommandProperties setPermission(String permission) {
		this.permission = permission;
		return this;
	}

	public CommandProperties setUsage(String usage) {
		this.usage = usage;
		return this;
	}

	public CommandProperties setAdmin() {
		this.admin = true;
		return this;
	}

	public CommandProperties setPlayersOnly() {
		this.playersOnly = true;
		return this;
	}

	public String getPrimaryAlias() {
		return aliases.length > 0 ? aliases[0] : null;
	}

	public String[] getAliases() {
		return aliases;
	}

	public String getDescription() {
		return description;
	}

	public String getPermission() {
		return permission;
	}

	public String getUsage() {
		return usage;
	}

	public boolean isAdmin() {
		return admin;
	}

	public boolean isPlayersOnly() {
		return playersOnly;
	}

}