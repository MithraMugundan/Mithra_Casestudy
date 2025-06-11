import { Component } from '@angular/core';
import { Player } from 'src/app/model/players';
import { PlayersService } from 'src/app/services/players.service';

@Component({
  selector: 'app-add-player',
  templateUrl: './add-player.component.html',
  styleUrls: ['./add-player.component.css']
})
export class AddPlayerComponent {

  constructor(private playerService: PlayersService) {}

  insertPlayer(data: Player) {
    this.playerService.insert(data)
      .subscribe(
        (player) => {
          console.log('Added Player is:', player);
          alert('Player added successfully!');
        },
        (error) => {
          console.error('Error while adding player:', error);
          alert('Failed to add player.');
        }
      );
  }
}
