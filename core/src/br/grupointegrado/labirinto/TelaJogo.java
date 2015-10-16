package br.grupointegrado.labirinto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by dougl on 11/09/2015.
 */
public class TelaJogo extends TelaBase {

    private OrthographicCamera camera;
    private ShapeRenderer renderer;

    private Array<Array<Bloco>> linhas = new Array<Array<Bloco>>();

    public TelaJogo(MainGame game) {
        super(game);
    }

    @Override
    public void show() {
        renderer = new ShapeRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        preencherCampo();
    }

    private void preencherCampo() {
        preencherLinha(BlocoTipo.VAZIO, BlocoTipo.DUPLO, BlocoTipo.VAZIO, BlocoTipo.VAZIO, BlocoTipo.VAZIO, BlocoTipo.VAZIO);
        preencherLinha(BlocoTipo.SIMPLES, BlocoTipo.SIMPLES, BlocoTipo.VAZIO, BlocoTipo.VAZIO, BlocoTipo.VAZIO, BlocoTipo.VAZIO);
        preencherLinha(BlocoTipo.SIMPLES, BlocoTipo.VAZIO, BlocoTipo.VAZIO, BlocoTipo.VAZIO, BlocoTipo.VAZIO, BlocoTipo.VAZIO);
        preencherLinha(BlocoTipo.SIMPLES, BlocoTipo.DUPLO, BlocoTipo.SIMPLES, BlocoTipo.SIMPLES, BlocoTipo.SIMPLES, BlocoTipo.VAZIO);
        preencherLinha(BlocoTipo.BARREIRA, BlocoTipo.DUPLO, BlocoTipo.VAZIO, BlocoTipo.VAZIO, BlocoTipo.SIMPLES, BlocoTipo.VAZIO);
        preencherLinha(BlocoTipo.SIMPLES, BlocoTipo.DUPLO, BlocoTipo.VAZIO, BlocoTipo.VAZIO, BlocoTipo.SIMPLES, BlocoTipo.VAZIO);
        preencherLinha(BlocoTipo.SIMPLES, BlocoTipo.SIMPLES, BlocoTipo.SIMPLES, BlocoTipo.SIMPLES, BlocoTipo.SIMPLES, BlocoTipo.VAZIO);
    }

    private void preencherLinha(BlocoTipo... tipos) {
        // pega posição Y desse bloco
        int posY = linhas.size;
        Array<Bloco> linha = new Array<Bloco>();
        for (BlocoTipo tipo : tipos) {
            int posX = linha.size;
            Vector2 posicao = new Vector2(posX, posY);
            switch (tipo) {
                case SIMPLES:
                    linha.add(new Bloco(posicao, tipo, 1));
                    break;
                case DUPLO:
                    linha.add(new Bloco(posicao, tipo, 2));
                    break;
                default:
                    linha.add(new Bloco(posicao, tipo, 0));
                    break;
            }
        }
        linhas.add(linha);
    }

    private float tam = 50, initX = 50, initY = 50;


    @Override
    protected void onRender(float delta) {
        Gdx.gl.glClearColor(.15f, .15f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < linhas.size; i++) {
            Array<Bloco> linha = linhas.get(i);
            for (int j = 0; j < linha.size; j++) {
                Bloco bloco = linha.get(j);

                switch (bloco.getTipo()) {
                    case VAZIO:
                        renderer.setColor(0f, 0f, 1f, 1);
                        break;
                    case BARREIRA:
                        renderer.setColor(0f, 0f, 0f, 1);
                        break;
                    case SIMPLES:
                        renderer.setColor(1f, 1f, 1f, 1);
                        break;
                    case DUPLO:
                        renderer.setColor(.5f, .5f, .5f, 1);
                        break;
                }

                float bordaX =  10 * bloco.getPosicao().x;
                float x = initX + bloco.getPosicao().x * tam + bordaX;
                float bordaY =  10 * bloco.getPosicao().y;
                float y = initY + bloco.getPosicao().y * tam + bordaY;

                renderer.rect(x, y, tam, tam);
            }
        }
        renderer.end();
    }

    @Override
    protected void onUpdate(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
