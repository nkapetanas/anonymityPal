import { Component, OnInit } from '@angular/core';
import { PrivacyService } from 'src/app/core/services/privacy/privacy-service.service';

@Component({
    selector: 'app-privacy-check',
    templateUrl: './privacy-check.component.html',
    styleUrls: ['./privacy-check.component.scss']
})
export class PrivacyCheckComponent implements OnInit {

    privacyAttributes: { kAnonymityParam: number | null, lDiversityParam: number | null } = { kAnonymityParam: null, lDiversityParam: null };

    constructor(
        private privacyService: PrivacyService
    ) { }

    ngOnInit(): void {
    }

    getPrivacyCheck() {
        this.privacyService.getQueryResultsPrivacyChecked(this.privacyAttributes).subscribe(response => {
            console.log(response);
        })
    }

}
