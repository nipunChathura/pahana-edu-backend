package com.icbt.pahanaedu.request;

import com.icbt.pahanaedu.dto.BookDetailsDto;
import lombok.Data;

@Data
public class BookRequest extends Request {
    private BookDetailsDto bookDetail;
}
