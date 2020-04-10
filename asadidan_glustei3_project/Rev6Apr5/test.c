#include "ai.c"
#include <unistd.h>
#include <time.h>
#define MAX(n1, n2) n1 > n2 ? n1 : n2

void play_game_to_file(FILE* f) {
    // printf("starting\n");
    //board initialized, struct has pointer to snek
    GameBoard* board = init_board();
    // show_board(board);

    int axis = AXIS_INIT;
    int direction = DIR_INIT;

    int play_on = 1;

    while (play_on){
        int n = MAX(8, 0.25*board->snek->length); // this controls how many moves are predicted ahead
        int* move = survival(board, n);
        // show_board(board);
        play_on = advance_frame(move[0], move[1], board);
        free(move);
    }
    end_game(&board, f);
}

void print_100_games_to_file(char* fn) {
    int delay;
    FILE* f  = fopen(fn, "w");
    int start = clock();
    for (int i = 0; i < 10; i++)
    {
        play_game_to_file(f);
        delay = 1000000;
        usleep(delay); // to ensure randomness
    }
    int end = clock();
    fclose(f);
}

int main(){
    print_100_games_to_file("c25.txt");
    return 0;
}
