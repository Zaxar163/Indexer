package ru.zaxar163.indexer.command.manage;

import org.javacord.api.entity.message.Message;

import ru.zaxar163.indexer.Indexer;
import ru.zaxar163.indexer.RoleManager;
import ru.zaxar163.indexer.command.Command;
import ru.zaxar163.indexer.command.CommandManager;
import ru.zaxar163.indexer.module.SwearFilter;

public class SwearFilterCommand extends Command {
	private final Indexer indexer;

	public SwearFilterCommand(final Indexer indexer) {
		super("swearfilter", "`!swearfilter` - включает/выключает фильтр мата в канале");
		this.indexer = indexer;
	}

	@Override
	public boolean canUse(final Message message) {
		return RoleManager.hasRole(indexer.roler.middleDeveloper, message.getUserAuthor());
	}

	@Override
	public void onCommand(final Message message, final String[] args) throws Exception {
		final SwearFilter swearFilter = indexer.swearFilter;
		if (!swearFilter.isEnabled()) {
			message.getChannel().sendMessage("Фильтр мата не настроен.");
			message.delete();
			return;
		}
		if (args.length < 1)
			throw new IllegalArgumentException("Illegal args");
		message.getChannel().asServerTextChannel().get().getServer().getChannels().stream()
				.filter(CommandManager.stc::isInstance).map(CommandManager.stc::cast)
				.filter(e -> e.getName().toLowerCase().contains(args[0])).forEach(e -> {
					if (swearFilter.isActive(e)) {
						swearFilter.disableFor(e);
						e.sendMessage("Фильтр мата для этого канала теперь **отключен**");
					} else {
						swearFilter.enableFor(e);
						e.sendMessage("Фильтр мата для этого канала теперь **включен**");
					}
				});
		message.delete();
	}
}
