package bzh.strawberry.api.command;

/*
 * This file AbstractCommand is part of a project StrawAPI.api.
 * It was created on 07/07/2020 00:09 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public abstract class AbstractBCommand extends Command {

    protected final Plugin plugin;
    protected final String permission;

    public AbstractBCommand(Plugin plugin, String name, String permission) {
        super(name);
        this.plugin = plugin;
        this.permission = permission;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!commandSender.hasPermission(this.permission)) {
            commandSender.sendMessage("§cVous n'avez pas les droits nécessaires pour effectuer cette commande (REQUIRE_{permission})".replace("{permission}", permission.toUpperCase()));
            return;
        }
        onCommand(commandSender, strings);
    }

    protected abstract boolean onCommand(CommandSender sender, String[] arguments);
}
