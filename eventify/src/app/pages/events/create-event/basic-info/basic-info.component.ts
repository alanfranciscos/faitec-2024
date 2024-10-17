import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HeaderComponent } from '../../../../components/header/header.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-basic-info',
  standalone: true,
  imports: [RouterLink, HeaderComponent, CommonModule, FormsModule],
  templateUrl: './basic-info.component.html',
  styleUrls: ['./basic-info.component.scss'],
})
export class BasicInfoComponent {
  eventName: string = '';
  eventDescription: string = '';
  startDate: string = '';
  finishDate: string = '';
  eventImage: string = '/assets/svg/logo.svg'; // Imagem inicial padrão

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      this.registerForm.patchValue({ profileImage: file });

      const reader = new FileReader();
      reader.onload = (e: ProgressEvent<FileReader>) => {
        const imageData = e.target?.result;
        this.userImage = (imageData as string) || '/assets/svg/avatar.svg';
      };
      reader.readAsDataURL(file);
    }
  }

  // Método para capturar os dados e navegar para a próxima página
  onAddAddress() {
    const eventData = {
      name: this.eventName,
      description: this.eventDescription,
      startDate: this.startDate,
      finishDate: this.finishDate,
    };

    console.log('Event Data:', eventData); // Apenas para verificação
    // Navegar para a próxima rota (ainda não implementado)
  }
}

// import { Component } from '@angular/core';
// import { RouterLink } from '@angular/router';
// import { HeaderComponent } from '../../../../components/header/header.component';
// import { CommonModule } from '@angular/common';
// import { FormsModule } from '@angular/forms';

// @Component({
//   selector: 'app-basic-info',
//   standalone: true,
//   imports: [RouterLink, HeaderComponent, CommonModule, FormsModule],
//   templateUrl: './basic-info.component.html',
//   styleUrls: ['./basic-info.component.scss'],
// })
// export class BasicInfoComponent {
//   eventName: string = '';
//   eventDescription: string = '';
//   startDate: string = '';
//   finishDate: string = '';
//   eventImage: string = '/assets/svg/logo.svg'; // Imagem inicial padrão

//   // Função para selecionar e exibir a imagem em base64
//   onFileSelected(event: Event) {
//     const input = event.target as HTMLInputElement;
//     if (input.files && input.files.length > 0) {
//       const file = input.files[0];
//       const reader = new FileReader();

//       reader.onload = (e: ProgressEvent<FileReader>) => {
//         const imageData = e.target?.result as string; // Base64 da imagem
//         this.eventImage = imageData; // Atualiza a imagem com os dados carregados em base64
//       };

//       reader.readAsDataURL(file); // Lê o arquivo selecionado como DataURL (Base64)
//     }
//   }

//   // Método para capturar os dados e navegar para a próxima página
//   onAddAddress() {
//     const eventData = {
//       name: this.eventName,
//       description: this.eventDescription,
//       startDate: this.startDate,
//       finishDate: this.finishDate,
//       image: this.eventImage, // A imagem está em base64
//     };

//     console.log('Event Data:', eventData); // Apenas para verificação
//     // Navegar para a próxima rota (ainda não implementado)
//   }
// }
