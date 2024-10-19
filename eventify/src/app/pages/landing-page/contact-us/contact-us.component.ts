import { Component } from '@angular/core';
import emailjs, { EmailJSResponseStatus } from 'emailjs-com';

@Component({
  selector: 'app-contact-us',
  standalone: true,
  templateUrl: './contact-us.component.html',
  styleUrls: ['./contact-us.component.scss'],
})
export class ContactUsComponent {
  sendEmail(event: Event) {
    event.preventDefault();

    const target = event.target as HTMLFormElement;
    const name = target['user_name'].value;
    const email = target['user_email'].value;
    const message = target['message'].value;
    const templateParams = {
      from_name: name,
      from_email: email,
      message: message,
    };

    emailjs
      .send(
        'service_j3s369j',
        'template_x9e7ed5',
        templateParams,
        'P26JSEYQLvdiCZ6MO'
      )
      .then(
        (response) => {
          alert('Email enviado com sucesso!');
          console.log(
            'Email enviado com sucesso!',
            response.status,
            response.text
          );
          target.reset();
        },
        (error) => {
          console.log('Erro ao enviar o email:', error);
        }
      );
  }
}
