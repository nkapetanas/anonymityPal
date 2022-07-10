import { Injectable } from '@angular/core';
import { Message, MessageService } from 'primeng/api';

@Injectable()
export class MessagesService {

    constructor(
        private messageService: MessageService
    ) { }
    
    showMessage(message: Message) {
        this.messageService.add(message);
    }
}