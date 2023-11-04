export class MessageDTO {
    id: string='';
    timestamp: number=0;
    from: string='';
    to: string[]=[];
    type: string='';
    body: string='';
}
