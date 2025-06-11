import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Player } from 'src/app/model/players';
import { PlayersService } from 'src/app/services/players.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  playerSearchList: Player[] = [];

  constructor(private route: ActivatedRoute, private playerService: PlayersService) {}

  ngOnInit(): void {
    this.findPlayerByIdFromRoute();
  }

  findPlayerByIdFromRoute() {
    this.route.params.subscribe(param => {
      const playerId = Number(param['input']); // 'input' from route like /search/3
      if (!isNaN(playerId)) {
        this.playerService.getPlayerById(playerId).subscribe(
          (player: Player) => {
            this.playerSearchList = [player]; // convert object to array
          },
          (error) => {
            console.error('Error fetching player:', error);
            this.playerSearchList = []; // clear on error
          }
        );
      }
    });
  }
}
