//
// Created by fryant on 2018/12/23.
//

#include "main.h"
#include <string.h>
#include <stdio.h>

#define FRAME_SIZE 480


int dn(const char* infile,const char* outfile){
    int i;
    int first = 1;
    float x[FRAME_SIZE];
    FILE *f1, *fout;
    DenoiseState *st;
    st = rnnoise_create();
    /*
    if (argc!=3) {
        fprintf(stderr, "usage: %s <noisy speech> <output denoised>\n", argv[0]);
        return 1;
    }
     */
    f1 = fopen(infile, "r");
    fout = fopen(outfile, "w");
    while (1) {
        short tmp[FRAME_SIZE];
        fread(tmp, sizeof(short), FRAME_SIZE, f1);
        if (feof(f1)) break;
        for (i=0;i<FRAME_SIZE;i++) x[i] = tmp[i];
        rnnoise_process_frame(st, x, x);
        for (i=0;i<FRAME_SIZE;i++) tmp[i] = x[i];
        if (!first) fwrite(tmp, sizeof(short), FRAME_SIZE, fout);
        first = 0;
    }
    rnnoise_destroy(st);
    fclose(f1);
    fclose(fout);
    return 0;
}