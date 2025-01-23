package com.antonioProjeto;

import java.util.NoSuchElementException;
import java.util.*;

public class ArvBinBusca<Chave extends Comparable<Chave>, Valor>
{
    private No raiz; /* Raiz da �rvore. */

    private class No
    {
        private Chave chave; /* Chave usada nas compara��es. */
        private Valor valor; /* Informa��o armazenada. */
        private No esq, dir; /* Refer�ncias para sub�rvores esquerda e direita. */

        /* Cria um n� com chave e valor fornecidos e esq = dir = null. */
        public No(Chave chave, Valor valor)
        {
            this.chave = chave;
            this.valor = valor;
            this.esq = null;
            this.dir = null;
        }

        /* Cria um n� com chave e valor fornecidos. As sub�rvores esq e dir s�o
         * passadas por par�metro. */
        public No(Chave chave, Valor valor, No esq, No dir)
        {
            this.chave = chave;
            this.valor = valor;
            this.esq = esq;
            this.dir = dir;
        }
    }

    /**
     *  Cria��o de uma �rvore vazia.
     */
    public ArvBinBusca()
    {
        raiz = null;
    }

    /**
     * Verifica se a �rvore est� vazia.
     *
     * @return se a �rvore est� vazia ou possui algum elemento
     */
    public boolean vazia()
    {
        return (raiz == null);
    }

    /**
     * Apresenta o conte�do da �rvore em ordem sim�trica.
     */
    public void mostra()
    {
        mostra(raiz);
    }

    private void mostra(No x)
    {
        /* Caso base (crit�rio de parada). */
        if(x == null)
            return;

        System.out.print("[");

        /* Chamada recursiva para a esquerda. */
        mostra(x.esq);

        /* Imprime a chave do n� corrente. */
        System.out.print("<" + x.chave + ">");

        /* Chamada recursiva para a direita. */
        mostra(x.dir);

        System.out.print("]");
    }

    /**
     * Retorna a menor chave da �rvore.
     *
     * @return a menor chave da �rvore
     * @throws NoSuchElementException se a �rvore est� vazia
     */
    public Chave min()
    {
        if(vazia())
            throw new NoSuchElementException("�rvore est� vazia!");

        return min(raiz).chave;
    }

    private No min(No x) {
        if (x.esq == null)
            return x;
        else
            return min(x.esq);
    }

    /**
     * Retorna o maior elemento da �rvore.
     *
     * @return o maior elemento da �rvore
     * @throws NoSuchElementException se a �rvore est� vazia
     */
    public Chave max() {
        if(vazia())
            throw new NoSuchElementException("A �rvore est� vazia!");

        return max(raiz).chave;
    }

    private No max(No x) {
        if (x.dir == null)
            return x;
        else
            return max(x.dir);
    }

    /**
     * Retorna o n�mero de n�s, isto �, pares (chave, valor), contidos na �rvore
     * bin�ria de busca.
     *
     * @return o n�mero de n�s da �rvore
     */
    public int tamanho()
    {
        return tamanho(raiz);
    }

    private int tamanho(No x)
    {
        /* Caso base (crit�rio de parada). */
        if(x == null)
            return 0;

        /* Chamada recursiva para sub�rvores esquerda e direita. */
        return 1 + tamanho(x.esq) + tamanho(x.dir);
    }


    public int altura()
    {
        return altura(raiz);
    }

    private int altura(No x)
    {
        if(x == null)
            return -1;

        int maxAltura = Math.max(altura(x.esq), altura(x.dir));

        return 1 + maxAltura;
    }



    public boolean contem(Chave chave) {
        if (chave == null)
            throw new IllegalArgumentException("A chave fornecida � null!");

        return get(chave) != null;
    }


    public Valor get(Chave chave)
    {
        return get(raiz, chave);
    }

    private Valor get(No x, Chave chave)
    {
        if(chave == null)
            throw new IllegalArgumentException("A chave fornecida � null!");

        /* A chave n�o se encontra na �rvore. */
        if(x == null)
            return null;

        int cmp = chave.compareTo(x.chave);

        if(cmp < 0) /* Deve-se ir para a esquerda. */
            return get(x.esq, chave);
        else if(cmp > 0) /* Deve-se ir para a direita. */
            return get(x.dir, chave);
        else /* Chave encontrada. */
            return x.valor;
    }

    public boolean put(Chave chave, Valor valor)
    {
        if(chave == null)
            throw new IllegalArgumentException("A chave fornecida � null!");

        if(valor == null) {
            delete(chave);
            return false;
        }

        raiz = put(raiz, chave, valor);
        return raiz != null;
    }

    private No put(No x, Chave chave, Valor valor)
    {
        /* Caso base: encontrou a posi��o de inser��o. */
        if (x == null)
            return new No(chave, valor);

        int cmp = chave.compareTo(x.chave);

        if(cmp < 0) /* Deve-se ir para a esquerda. */
            x.esq = put(x.esq, chave, valor);
        else if(cmp > 0) /* Deve-se ir para a direita. */
            x.dir = put(x.dir, chave, valor);
        else /* Caso tenha encontrado n� de mesma chave. */
            return null;

        return x;
    }


    public void deleteMin()
    {
        if(vazia())
            throw new NoSuchElementException("A �rvore est� vazia!");

        raiz = deleteMin(raiz);
    }

    /* M�todo recursivo que anda sempre para a esquerda, procurando o n�
     * de menor chave a ser removido. */
    private No deleteMin(No x)
    {
        /* Caso n�o haja filho � esquerda, o n� corrente possui a menor chave. */
        if(x.esq == null)
            return x.dir;

        x.esq = deleteMin(x.esq);

        return x;
    }


    public void deleteMax()
    {
        if(vazia())
            throw new NoSuchElementException("A �rvore est� vazia!");

        raiz = deleteMax(raiz);
    }


    private No deleteMax(No x)
    {
        if(x.dir == null)
            return x.esq;

        x.dir = deleteMax(x.dir);

        return x;
    }


    public void delete(Chave chave)
    {
        raiz = delete(raiz, chave);
    }

    /* Remove o n� com o valor "val" da "�rvore" a partir do n� para o qual est� sendo chamada a fun��o. Obs: "ref_no" � o ponteiro que referencia o n� para o qual est� sendo chamada a fun��o, o qual pode ter que ser modificado. Retorna false se o valor n�o pertencer � "�rvore".
     */
    private No delete(No x, Chave chave)
    {
        if (x == null)
            return null;

        int cmp = chave.compareTo(x.chave);

        if(cmp < 0)
            x.esq = delete(x.esq, chave);
        else if(cmp > 0)
            x.dir = delete(x.dir, chave);
        else
        {
            if(x.dir == null)
                return x.esq;
            if(x.esq  == null)
                return x.dir;

            No t = x;

            /* Pega o menor da sub�rvore direita (mais � esquerda). */
            x = min(t.dir);

            /* Remove o menor. */
            x.dir = deleteMin(t.dir);

            /* A sub�rvore esquerda se mant�m a mesma. */
            x.esq = t.esq;
        }

        return x;
    }

    public void deleteAntecessor(Chave chave)
    {
        raiz = deleteAntecessor(raiz, chave);
    }

    /* Remove o n� com o valor "val" da "�rvore" a partir do n� para o qual est� sendo chamada a fun��o. Obs: "ref_no" � o ponteiro que referencia o n� para o qual est� sendo chamada a fun��o, o qual pode ter que ser modificado. Retorna false se o valor n�o pertencer � "�rvore".
     */
    private No deleteAntecessor(No x, Chave chave)
    {
        if (x == null)
            return null;

        int cmp = chave.compareTo(x.chave);

        if(cmp < 0)
            x.esq = deleteAntecessor(x.esq, chave);
        else if(cmp > 0)
            x.dir = deleteAntecessor(x.dir, chave);
        else
        {
            if(x.dir == null)
                return x.esq;
            if(x.esq  == null)
                return x.dir;

            No t = x;

            x = max(t.esq);

            x.esq = deleteMax(t.esq);

            x.dir = t.dir;
        }

        return x;
    }



    public Chave piso(Chave chave)
    {
        if (chave == null)
            throw new IllegalArgumentException("A chave fornecida � null!");

        if (vazia())
            throw new NoSuchElementException("A �rvore est� vazia!");

        No x = piso(raiz, chave);

        if (x == null)
            return null;
        else
            return x.chave;
    }

    private No piso(No x, Chave chave)
    {
        if (x == null)
            return null;

        int cmp = chave.compareTo(x.chave);

        if (cmp == 0)
            return x;

        if (cmp <  0)
            return piso(x.esq, chave);

        No t = piso(x.dir, chave);

        if (t != null)
            return t;
        else
            return x;
    }



    public Chave topo(Chave chave)
    {
        if (chave == null)
            throw new IllegalArgumentException("A chave fornecida � null!");

        if (vazia())
            throw new NoSuchElementException("A �rvore est� vazia!");

        No x = topo(raiz, chave);

        if (x == null)
            return null;
        else
            return x.chave;
    }

    private No topo(No x, Chave chave)
    {
        if (x == null)
            return null;

        int cmp = chave.compareTo(x.chave);

        if (cmp == 0)
            return x;

        if (cmp < 0)
        {
            No t = topo(x.esq, chave);

            if (t != null)
                return t;
            else
                return x;
        }

        return topo(x.dir, chave);
    }



    public int rank(Chave chave) {
        /* O aluno deve fazer como exerc�cio. */
        return 0;
    }

    // N�mero de chaves na sub�rvore que s�o estritamente menores do que chave.
    private int rank(Chave chave, No x) {
        /* O aluno deve fazer como exerc�cio. */
        return 0;
    }

    public void removeLaura(int qtdNos){
        Random gerador = new Random();
        Random antSuc = new Random();
        for(int i = 0; i < (qtdNos * qtdNos); i++){
            int antigoNo;
            do{
                antigoNo = gerador.nextInt(100,1000000);
            }while(!this.contem((Chave) Integer.valueOf(antigoNo)));
            No no = new No ((Chave) Integer.valueOf(antigoNo),(Valor) Integer.valueOf(antigoNo));
            int eAntSuc = antSuc.nextInt(0,1);
            if(eAntSuc == 0)
                this.delete(no, no.chave);
            else
                this.deleteAntecessor(no, no.chave);
            int novoNo;
            do{
                novoNo = gerador.nextInt(100,1000000);
            }while(this.contem((Chave) Integer.valueOf(novoNo)));
            this.put((Chave) Integer.valueOf(novoNo),(Valor) Integer.valueOf(novoNo));
        }
    }
}