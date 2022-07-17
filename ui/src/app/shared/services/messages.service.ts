import { Injectable } from '@angular/core';
import { Message, MessageService } from 'primeng/api';

@Injectable()
export class MessagesService {

    constructor(
        private messageService: MessageService
    ) { }
    
    showSuccessMessage(summary: string) {
        this.messageService.add({ severity: 'success', summary: summary });
    }

    showErrorMessage(summary: string) {
        this.messageService.add({ severity: 'error', summary: summary, sticky: true });
    }

    showInfoMessage(summary: string) {
        this.messageService.add({ severity: 'info', summary: summary, sticky: true });
    }
}