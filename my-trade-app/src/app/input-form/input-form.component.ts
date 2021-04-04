import { Component, createPlatformFactory, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { HttpClient} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

@Component({
  selector: 'app-input-form',
  templateUrl: './input-form.component.html',
  styleUrls: ['./input-form.component.css']
})


export class InputFormComponent implements OnInit {

  private usersUrl: string;
  fetchedData: any;

  constructor( private formBuilder: FormBuilder,
                private http: HttpClient) {

                  this.usersUrl = 'http://localhost:8080/save';

  }

  tradeForm = new FormGroup({
    tradeId: new FormControl(),
    version: new FormControl(),
    counterParty: new FormControl(),
    bookId: new FormControl(),
    maturityDate: new FormControl(),
    createdDate: new FormControl(),
    expiredFlag: new FormControl(),
  });

  ngOnInit(): void {
    this.fetchTrade();
    this.tradeForm.reset();
  }

  onSubmit() {
    console.log(this.tradeForm.value);
    this.http.post("http://localhost:8080/save", this.tradeForm.value).subscribe(
        data => {
          console.log("Data Saved= "+ data);
          this.fetchTrade();
      },
      error => {
        window.alert("Please check server error");
      }
    );

  }

  fetchTrade () {
    console.log("Fetching trades");
    this.http.get("http://localhost:8080/get").subscribe(
        data => { console.log("Data fetched = "+ data);
        this.fetchedData = data;

      }
    );
  }


}

