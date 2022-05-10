import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'app-query-cards-selectors',
    templateUrl: './query-cards-selectors.component.html',
    styleUrls: ['./query-cards-selectors.component.scss']
})
export class QueryCardsSelectorsComponent implements OnInit {

    constructor(
        private router: Router,
        private route: ActivatedRoute
    ) { }

    ngOnInit(): void {

    }

    createCustomQuestion() {
        this.router.navigate(['custom-query'], { relativeTo: this.route })
    }

    createNativeQuery() {
        this.router.navigate(['native-query'], { relativeTo: this.route })
    }
}
