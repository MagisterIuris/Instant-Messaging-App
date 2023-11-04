package fr.mightycode.cpoo.server.dto;

import java.util.List;
import java.util.UUID;

public record ConversationDisplayDTO(UUID id, String from, List<String> usernames, List<String> pictures, String lastMessage, long timestamp) {
}