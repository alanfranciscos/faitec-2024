export interface UserCredential {
    // email: string,
    // password: string
    id?: string;
    name?: string; // Nome do usuário
    password: string; //Senha do usuário
    nickname?: string; // Apelido do usuário
    email: string; // Email do usuário
    city?: string; // Cidade do usuário
    state?: string; // Estado do usuário (UF)
}