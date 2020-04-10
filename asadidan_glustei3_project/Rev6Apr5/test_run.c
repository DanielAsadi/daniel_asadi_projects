#include "game_AI.c"
#include <unistd.h>
#include <time.h>

#define MAX(n1, n2) n1 > n2 ? n1 : n2

static int score[100]; //change to just normal var in run100times

typedef struct avg_info {
    double avg_score;
    double avg_time;
} avg_info;

int play_game(int n) {
    GameBoard *board = init_board();
    // show_board(board);
    int axis = AXIS_INIT;
    int direction = DIR_INIT;
    int play_on = 1;
    while (play_on) {
        n = MAX(8, 0.25*board->snek->length);
        int *move = survival(board, n);
        play_on = advance_frame(move[0], move[1], board);
        free(move);
    }
    int num_moog = get_moogles_eaten();
    end_game(&board);
    return num_moog;
}

int play_game_var_n(double factor) {
    int n;
    GameBoard *board = init_board();
    // show_board(board);
    int axis = AXIS_INIT;
    int direction = DIR_INIT;
    int play_on = 1;
    while (play_on) {
        n = (factor - (int) factor == 0) ? ((int) factor * get_length()) : ((int) (factor * (double) get_length()) +
                                                                            1); //ceil
        int *move = survival(board, n);
        play_on = advance_frame(move[0], move[1], board);
    }
    int num_moog = get_moogles_eaten();
    end_game(&board);
    return num_moog;
}

avg_info run_x_games(int n, int num_games) {
    int delay = 1000000;
    int sum = 0;
    //int start = clock();
    clock_t start = clock();
    for (int i = 0; i < num_games; i++) {
        score[i] = play_game(n);
        printf("num moogles: %d\n", score[i]);
        sum += score[i];
        usleep(delay); // to ensure randomness
    }
    //int end = clock();
    clock_t end = clock();
    double cpu_time_used = ((double) ((end - start)) / CLOCKS_PER_SEC - ((delay / 1000000) * num_games));
    //avg_info avg = {(double) sum / num_games, ((double) (end - start)) / (CLOCKS_PER_SEC * num_games) - delay / 2000000};
    avg_info avg = {(double) sum / num_games, cpu_time_used};
    return avg;
}

avg_info run_x_games_var_b(int n, int num_games) {
    int delay = 1000000;
    int sum = 0;
    //int start = clock();
    clock_t start = clock();
    for (int i = 0; i < num_games; i++) {
        score[i] = play_game(n);
        printf("num moogles: %d\n", score[i]);
        sum += score[i];
        usleep(delay); // to ensure randomness
    }
    //int end = clock();
    clock_t end = clock();
    double cpu_time_used = ((double) ((end - start)) / CLOCKS_PER_SEC - ((delay / 1000000) * num_games));
    //avg_info avg = {(double) sum / num_games, ((double) (end - start)) / (CLOCKS_PER_SEC * num_games) - delay / 2000000};
    avg_info avg = {(double) sum / num_games, cpu_time_used};
    return avg;
}

avg_info run_x_games_var_n(double factor, int num_games) {
    int delay = 1000000;
    int sum = 0;
    int start = clock();
    for (int i = 0; i < num_games; i++) {
        score[i] = play_game_var_n(factor);
        printf("num moogles: %d\n", score[i]);
        sum += score[i];
        usleep(delay); // to ensure randomness
    }
    int end = clock();
    avg_info avg = {(double) sum / num_games, ((double) (end - start)) / (CLOCKS_PER_SEC * 100) - delay / 2000000};
    return avg;
}

void n_vs_avg_score(char *fn, int n_max) {
    int num_games = 100;
    FILE *f = fopen(fn, "w");
    for (int n = 8; n <= n_max; n++) {
        avg_info avg = run_x_games(n, num_games);
        printf("================> avg score: %.2f\n", avg.avg_score);
        printf("================> avg time: %f sec\n\n", avg.avg_time);
        fprintf(f, "%d %.2f\n", n, avg.avg_score);
    }
    fclose(f);
}

void n_vs_avg_time(char *fn, int n_max) {
    int num_games = 100;
    FILE *f = fopen(fn, "w");
    for (int n = 0; n <= n_max; n++) {
        avg_info avg = run_x_games(n, num_games);
        printf("================> avg score: %.2f\n", avg.avg_score);
        printf("================> avg time: %f sec\n\n", avg.avg_time);
        fprintf(f, "%d %.2f\n", n, avg.avg_time);
    }
    fclose(f);
}

void var_n_vs_avg_score(char *fn, double f_max) {
    int num_games = 100;
    int f_max_i = (int) (f_max * 100);
    double factor;
    FILE *f = fopen(fn, "w");
    for (int i = 10; i <= f_max_i; i += 5) {
        factor = (double) i / 100;
        avg_info avg = run_x_games_var_n(factor, num_games);
        printf("================> avg score: %.2f\n", avg.avg_score);
        printf("================> avg time: %f sec\n\n", avg.avg_time);
        fprintf(f, "%.2f %.2f\n", factor, avg.avg_score);
    }
    fclose(f);
}

void var_n_vs_avg_time(char *fn, double f_max) {
    int num_games = 100;
    int f_max_i = (int) (f_max * 100);
    double factor;
    FILE *f = fopen(fn, "w");
    for (int i = 10; i <= f_max_i; i += 5) {
        factor = (double) i / 100;
        avg_info avg = run_x_games_var_n(factor, num_games);
        printf("================> avg score: %.2f\n", avg.avg_score);
        printf("================> avg time: %f sec\n\n", avg.avg_time);
        fprintf(f, "%.2f %.2f\n", factor, avg.avg_time);
    }
    fclose(f);
}

void var_board_size(char *fn, int b_max) {
    int num_games = 100;
    FILE *f = fopen(fn, "w");
    for (BOARD_SIZE = 6; BOARD_SIZE <= b_max; BOARD_SIZE++) {
        avg_info avg = run_x_games_var_b(8, num_games);
        printf("================> avg score: %.2f\n", avg.avg_score);
        fprintf(f, "%d %.2f\n", BOARD_SIZE, avg.avg_score);
    }
    fclose(f);
}

void var_cycle_allowance(char *fn, int c_max) {
    int num_games = 100;
    int c_m = (int) c_max * 10;
    FILE *f = fopen(fn, "w");
    for (int c = 5; c <= c_m; c++) {
        CYCLE_ALLOWANCE = (double) c / 10;
        avg_info avg = run_x_games_var_b(8, num_games);
        printf("================> avg score: %.2f\n", avg.avg_score);
        fprintf(f, "%d %.2f\n", BOARD_SIZE, avg.avg_score);
    }
    fclose(f);
}

int main() {
    int n_max = 8; // this controls how many moves are predicted ahead
    double f_max = 0.2;
    int b_max = 15;
    double c_max = 2.5;
    //n_vs_avg_score("Rev6Apr5/avg_score12.txt", n_max);
    //n_vs_avg_time("Rev6Apr5/avg_time1.txt", n_max);
    //var_n_vs_avg_score("Rev6Apr5/avg_score2.txt", f_max);
    //var_n_vs_avg_time("Rev6Apr5/avg_time2.txt", f_max);
    //var_board_size("Rev6Apr5/b.txt", b_max);
    return 0;
}
