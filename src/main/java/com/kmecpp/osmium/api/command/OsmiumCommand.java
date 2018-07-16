package com.kmecpp.osmium.api.command;

import java.util.ArrayList;

import com.kmecpp.jlib.utils.StringUtil;

public class OsmiumCommand extends CommandProperties {

	private ArrayList<OsmiumCommand> args = new ArrayList<>();

	private String title = "&a&lCommand List";

	public OsmiumCommand(String... aliases) {
		super(aliases);
		//		this.properties = new CommandProperties(this.getClass().getAnnotation(Command.class));
	}

	public void configure() {
	}

	public final void setTitle(String title) {
		this.title = title;
	}

	public void execute(CommandEvent e) {
		if (!args.isEmpty()) {
			if (e.getArgs().length == 0) {
				e.sendMessage("");
				e.sendMessage(title);
				e.sendMessage("&e&m----------------------------------------");
				e.sendMessage("");
				for (OsmiumCommand arg : args) {
					e.sendMessage("&b/" + arg.getPrimaryAlias()
							+ (arg.getDescription().isEmpty() ? "" : "&e - &b" + arg.getDescription()));
				}
			} else {
				String arg = e.getArg(1);
				for (CommandProperties a : args) {
					for (String alias : a.getAliases()) {
						if (alias.equalsIgnoreCase(arg)) {
							//							a.execute();
						}
					}
				}
			}
		}
	}

	public final OsmiumCommand add(String... aliases) {
		return new OsmiumCommand(aliases);
	}

	public final void setArg(String label, CommandExecutor executor) {
		setArg(label, "", "", executor);
	}

	public final void setArg(String label, String usage, String description, CommandExecutor executor) {

	}

	public final void notFoundError(String type, String input) {
		throw new CommandException("&4Error: &c" + StringUtil.capitalize(type) + " not found: '" + input + "'");
	}

	public final void usageError() {
		throw CommandException.USAGE_ERROR;
	}

}
