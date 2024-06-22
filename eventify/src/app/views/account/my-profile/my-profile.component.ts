// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
// import { AuthenticationService } from '../../../services/authentication.service';
// import { UserProfile } from '../../../domain/dto/user-profile';

// @Component({
//   selector: 'app-my-profile',
//   templateUrl: './my-profile.component.html',
//   styleUrls: ['./my-profile.component.css'],
//   standalone: true,
//   imports: [ReactiveFormsModule
//   ],
// })
// export class MyProfileComponent implements OnInit {
//   profileForm: FormGroup;
//   isEditing = false;

//   constructor(private fb: FormBuilder, private userService: AuthenticationService) {
//     this.profileForm = this.fb.group({
//       name: [{ value: '', disabled: true }],
//       nickname: [{ value: '', disabled: true }],
//       email: [{ value: '', disabled: true }],
//       city: [{ value: '', disabled: true }],
//       state: [{ value: '', disabled: true }]
//     });
//   }

//   ngOnInit(): void {
//     this.userService.getUserProfile().then(user => {
//       this.profileForm.patchValue(user);
//     });
//   }

//   editProfile(): void {
//     this.isEditing = true;
//     this.profileForm.enable();
//   }

//   onSubmit(): void {
//     if (this.profileForm.valid) {
//       console.log('Perfil atualizado:', this.profileForm.value);
//       this.userService.updateUserProfile(this.profileForm.value).then(() => {
//         this.profileForm.disable();
//         this.isEditing = false;
//       });
//     }
//   }
// }

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthenticationService } from '../../../services/authentication.service';
import { UserCredential } from '../../../domain/dto/user-credential';
import { UserReadService } from '../../../services/user/user-read.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css'],
  standalone: true,
  imports: [ReactiveFormsModule
  ],
})
export class MyProfileComponent implements OnInit {
  user!: UserCredential;
  profileForm: FormGroup;
  isEditing = false;
  userId?: string;

  constructor(private fb: FormBuilder, private userService: AuthenticationService, private userReadService: UserReadService, private activatedRoute: ActivatedRoute,) {
    this.profileForm = this.fb.group({
      name: [{ value: '', disabled: true }],
      nickname: [{ value: '', disabled: true }],
      email: [{ value: '', disabled: true }],
      city: [{ value: '', disabled: true }],
      state: [{ value: '', disabled: true }]
    });
    this.initializeForm();
  }

  ngOnInit(): void {
    this.loadUser();
    this.editProfile();

  }
  async loadUser() {
    this.user = await this.userReadService.findById('1');
    // let userId = this.activatedRoute.snapshot.paramMap.get('id');
    // this.userId = userId!;
    // console.log(userId)
    // this.userReadService.findById(userId!);
  }
  editProfile(): void {
    this.isEditing = true;
    this.profileForm.enable();
  }
  initializeForm() {
    this.profileForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],
      nickname: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],
      email: ['', [Validators.required, Validators.email]],
      city: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],
      state: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(10)]]

    });
  }

  onSubmit(): void {
    // if (this.profileForm.valid) {
    //   console.log('Perfil atualizado:', this.profileForm.value);
    //   this.userService.updateUserProfile(this.profileForm.value).then(() => {
    //     this.profileForm.disable();
    //     this.isEditing = false;
    //   });
    // }
  }
}
