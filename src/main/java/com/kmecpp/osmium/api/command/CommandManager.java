package com.kmecpp.osmium.api.command;

import java.util.ArrayList;
import java.util.HashMap;

import com.kmecpp.osmium.BukkitAccess;
import com.kmecpp.osmium.SpongeAccess;
import com.kmecpp.osmium.api.logging.OsmiumLogger;
import com.kmecpp.osmium.api.platform.Platform;
import com.kmecpp.osmium.api.plugin.OsmiumPlugin;

public final class CommandManager {

	private HashMap<OsmiumPlugin, ArrayList<Command>> commands = new HashMap<>();

	public Command register(OsmiumPlugin plugin, Command command) {
		ArrayList<Command> pluginCommands = getCommands(plugin);
		pluginCommands.add(command);
		commands.put(plugin, pluginCommands);
		OsmiumLogger.debug("Registered Osmium command: /" + command.getPrimaryAlias());
		return command;
	}

	public HashMap<OsmiumPlugin, ArrayList<Command>> getCommands() {
		return commands;
	}

	public ArrayList<Command> getCommands(OsmiumPlugin plugin) {
		return commands.getOrDefault(plugin, new ArrayList<>());
	}

	public void processCommand(String command) {
		if (Platform.isBukkit()) {
			BukkitAccess.processConsoleCommand(command);
		} else if (Platform.isSponge()) {
			SpongeAccess.processConsoleCommand(command);
		}
	}

	public void processCommand(CommandSender sender, String command) {
		if (Platform.isBukkit()) {
			BukkitAccess.processCommand((org.bukkit.command.CommandSender) sender.getSource(), command);
		} else if (Platform.isSponge()) {
			SpongeAccess.processCommand((org.spongepowered.api.command.CommandSource) sender, command);
		}
	}

	public static boolean invokeCommand(Command command, CommandSender sender, String commandLabel, String[] args) {
		try {
			if (sender instanceof ConsoleCommandSender && !command.isConsole()) {
				throw CommandException.PLAYERS_ONLY;
			}

			CommandAction event = new CommandAction(sender, commandLabel, args);
			command.checkPermission(event);

			//Simple commands
			if (command.getArgs().isEmpty()) {
				command.execute(event);
			}

			//Commands with registered arguments
			else {
				if (args.length == 0) {
					command.sendHelp(event);
				} else {
					SimpleCommand arg = command.getArgumentMatching(args[0]);
					arg.checkPermission(event);
					event.consumeArgument();
					arg.execute(event);
				}
			}
			return true;
		} catch (CommandException e) {
			if (e == CommandException.USAGE_ERROR) {
				sender.sendMessage("&cUsage: " + command.getUsage());
			} else {
				sender.sendMessage("&c" + e.getMessage());
			}
			return false;
		}
	}

	public static void sendFailedRegistrationMessage(OsmiumPlugin plugin, Command command) {
		OsmiumLogger.warn("Unable to register /" + command.getPrimaryAlias() + " for plugin: " + plugin.getName() + " because its aliases are unavailable!");
		OsmiumLogger.warn("To correct this issue, create a command rewrite rule in the osmium config");
		OsmiumLogger.warn("commands." + command.getPrimaryAlias() + "={alternate}");

	}

	//	private static ArrayList<Command> commands = new ArrayList<>();
	//
	//	public CommandManager register(Class<? extends OsmiumCommand> command) {
	//		//		commands.add(Reflection.newInstance(command));
	//
	//		if (Platform.isBukkit()) {
	//
	//		} else if (Platform.isSponge()) {
	//
	//		}
	//		return this;
	//	}
	//
	//	public static CommandBuilder register(String... aliases) {
	//		return new CommandBuilder(aliases);
	//	}
	//
	//	public static class CommandBuilder {
	//
	//		private String[] aliases;
	//		private String permission;
	//		private String description;
	//
	//		public CommandBuilder(String[] aliases) {
	//			this.aliases = aliases;
	//		}
	//
	//		public CommandBuilder permission(String permission) {
	//			this.permission = permission;
	//			return this;
	//		}
	//
	//		public CommandBuilder description(String description) {
	//			this.description = description;
	//			return this;
	//		}
	//
	//		public void executor(CommandExecutor executor) {
	//			//			commands.add(new OsmiumCommand() {
	//			//
	//			//				@Override
	//			//				public void configure() {
	//			//					setAliases(aliases);
	//			//					setPermission(permission);
	//			//					setDescription(description);
	//			//				}
	//			//
	//			//				@Override
	//			//				public void execute(CommandSender sender, String label, String[] args) {
	//			//					executor.execute(sender, label, args);
	//			//				}
	//			//
	//			//			});
	//		}
	//
	//	}

	//	private static ArrayList<CommandBase> commands = new ArrayList<>();
	//	private static HashSet<String> overrides = new HashSet<>();
	//
	//	//	public static void registerCommand(String commandLabel, String[] aliases, Class<? extends Command> executor) {
	//	//		try {
	//	//			Command command = executor.newInstance();
	//	//			commands.add(command);
	//	//			command.registerCommand(commandLabel, aliases);
	//	//		} catch (Exception e) {
	//	//			e.printStackTrace();
	//	//		}
	//	//	}
	//
	//	public static void runCommand(CommandSender out, String commandStr) {
	//		String[] parts = commandStr.split(" ");
	//		if (parts.length > 0) {
	//			String label = parts[0];
	//			for (CommandBase command : commands) {
	//				for (String alias : command.getAliases()) {
	//					if (label.equalsIgnoreCase(alias)) {
	//						command.execute(out, label, Arrays.copyOfRange(parts, 1, parts.length));
	//					}
	//				}
	//			}
	//		}
	//	}
	//
	//	public static void registerOverrides(String... commands) {
	//		for (String command : commands) {
	//			overrides.add(command.toLowerCase());
	//		}
	//	}
	//
	//	public static boolean isOverriden(String command) {
	//		return overrides.contains(command.toLowerCase());
	//	}
	//
	//	/**
	//	 * Called to register the command directly into the main server command map
	//	 */
	//	public static final void register(Class<? extends CommandBase> commandClass) {
	//		//Code for getting current version
	//		//String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	//		try {
	//			CommandBase command = commandClass.newInstance();
	//			commands.add(command);
	//
	//			SimpleCommandMap commandMap = (SimpleCommandMap) Reflection.getFieldValue(Bukkit.getServer(), "commandMap");
	//			//			for (String alias : command.getAliases()) {
	//			//				for (org.bukkit.command.Command c : commandMap.getCommands()) {
	//			//					if (c.getAliases().contains(alias)) {
	//			//						CoreLogger.info("Unregistering command alias: " + alias);
	//			//						c.getAliases().remove(alias);
	//			//					}
	//			//				}
	//			//			}
	//
	//			commandMap.register(command.getName(), command.getCommand());
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}
	//
	//	public static ArrayList<CommandBase> getCommands() {
	//		return commands;
	//	}

}
