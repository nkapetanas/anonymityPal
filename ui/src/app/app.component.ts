import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})

export class AppComponent implements OnInit {

    constructor(private router: Router) { }

    ngOnInit() { }

    askQuestion() {
        this.router.navigate(['privacy-buddy/create-question']);
    }

    redirectToHome() {
        this.router.navigate(['privacy-buddy']);
    }
}
