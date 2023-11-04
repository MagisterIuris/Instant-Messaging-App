package fr.mightycode.cpoo.server.dto;

import java.util.List;

public record NewMessageDTO(List<String> to, String type, String body) {
}
