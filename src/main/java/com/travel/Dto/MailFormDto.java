package com.travel.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailFormDto {

	private String from;
    private String[] address;
    private String title;
    private String content;
}
